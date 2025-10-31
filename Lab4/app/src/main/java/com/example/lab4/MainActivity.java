package com.example.lab4;

import android.Manifest;
import android.app.ComponentCaller;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button galleryBtn;
Button photoBtn;
    ImageView profilePicture;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSharedPreferences("pfp", 0).edit().clear().commit();
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (!getSavedPfp().equals("nothing")){
            profilePicture.setImageURI(Uri.parse(getSavedPfp()));
        }

        galleryBtn = findViewById(R.id.galleryBtn);
        photoBtn = findViewById(R.id.photoBtn);
        profilePicture = findViewById(R.id.profilePicture);


        registerResult();
         checkStoragePermissions();

        galleryBtn.setOnClickListener(view -> pickImage());

        photoBtn.setOnClickListener(view -> openCamera());

        // request for camera
        /*
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                Manifest.permission.CAMERA
        }, 100);

         */
    }

    public String getSavedPfp(){
        SharedPreferences sharedPreferences = getSharedPreferences("pfp", MODE_PRIVATE);
        String possilbe_uri=sharedPreferences.getString("URI","nothing");
        return possilbe_uri;
    }

    private boolean checkCameraPermissions(){
        return ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkStoragePermissions() {
        return ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                200);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("requestperms",String.valueOf(requestCode));

        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with camera
            } else {
                // Permission denied
                Toast.makeText(this, "Camera permission is required to access the camera.", Toast.LENGTH_SHORT).show();
            }
        }

        else if (requestCode == 200) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with camera
            } else {
                // Permission denied
                Toast.makeText(this, "Storage permissions are required",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void pickImage(){
       Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
       resultLauncher.launch(intent);
    }

    private void registerResult(){
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                       try{
                            Uri imageUri = result.getData().getData();
                           SharedPreferences sharedPreferences = getSharedPreferences("pfp", MODE_PRIVATE);
                           SharedPreferences.Editor editor = sharedPreferences.edit();
                           editor.putString("URI", String.valueOf(imageUri));
                           editor.apply();
                            profilePicture.setImageURI(imageUri);
                       } catch (Exception e){
                           Toast.makeText(MainActivity.this,"No Image Selected",Toast.LENGTH_SHORT).show();
                       }
                    }
                }
        );
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data, @NonNull ComponentCaller caller) {
        super.onActivityResult(requestCode, resultCode, data, caller);
        if (requestCode == 100){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            SharedPreferences sharedPreferences = getSharedPreferences("pfp", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();


            Uri imageUri = bitmapToUri(this,bitmap);
            editor.putString("URI", String.valueOf(imageUri));

            String possilbe_uri=sharedPreferences.getString("URI","nothing");
            Log.d("Main Activity",possilbe_uri);
            Log.d("Main Activity","adsfafdasdfasdfasdf");
            editor.apply();
            profilePicture.setImageURI(imageUri);

        }
    }

    public static Uri bitmapToUri(Context context, Bitmap bitmap) {
        // Create a file in which to save the bitmap
        File imagesFolder = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "my_images");
        if (!imagesFolder.exists()) {
            imagesFolder.mkdirs(); // Create the directory if it doesn't exist
        }

        File file = new File(imagesFolder, "image_" + System.currentTimeMillis() + ".jpg");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            // Compress the bitmap and write it to the file output stream
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();

            // Return the Uri of the saved file
            return Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Handle the error appropriately in your code
        }
    }
}