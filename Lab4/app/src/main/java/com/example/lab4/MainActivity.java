package com.example.lab4;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.OutputStream;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button btnCamera, btnGallery;
    private RecyclerView recycler;
    private ImageAdapter adapter;

    private ActivityResultLauncher<String> cameraPermLauncher;
    private ActivityResultLauncher<String> galleryPermLauncher;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnCamera = findViewById(R.id.btnCamera);
        btnGallery = findViewById(R.id.btnGallery);
        recycler  = findViewById(R.id.recycler);

        adapter = new ImageAdapter();
        recycler.setLayoutManager(new GridLayoutManager(this, 3));
        recycler.setAdapter(adapter);

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Bitmap bmp = (Bitmap) result.getData().getExtras().get("data");
                        if (bmp != null) {
                            Uri saved = saveBitmapToMediaStore(bmp);
                            if (saved != null) adapter.add(saved);
                            toast(saved != null ? "Photo captured" : "Failed to save camera image");
                        } else toast("No image from camera");
                    } else toast("Camera canceled");
                });

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        if (uri != null) { adapter.add(uri); toast("Image added"); }
                        else toast("Invalid selection");
                    } else toast("Gallery canceled");
                });

        cameraPermLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                granted -> { if (granted) openCamera(); else toast("Camera permission denied"); });

        final String galleryPermission = (Build.VERSION.SDK_INT >= 33)
                ? Manifest.permission.READ_MEDIA_IMAGES
                : Manifest.permission.READ_EXTERNAL_STORAGE;

        galleryPermLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                granted -> { if (granted) openGallery(); else toast("Storage permission denied"); });



        btnCamera.setOnClickListener(v -> cameraPermLauncher.launch(Manifest.permission.CAMERA));
        btnGallery.setOnClickListener(v -> galleryPermLauncher.launch(galleryPermission));
    }

    private void openCamera() {
        try { cameraLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)); }
        catch (Exception e) { toast("Unable to open camera: " + e.getMessage()); }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
        try { galleryLauncher.launch(Intent.createChooser(intent, "Select Image")); }
        catch (Exception e) { toast("Unable to open gallery: " + e.getMessage()); }
    }

    private Uri saveBitmapToMediaStore(Bitmap bitmap) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(MediaStore.Images.Media.DISPLAY_NAME, "lab4_" + System.currentTimeMillis() + ".jpg");
            cv.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            cv.put(MediaStore.Images.Media.WIDTH, bitmap.getWidth());
            cv.put(MediaStore.Images.Media.HEIGHT, bitmap.getHeight());

            Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);
            if (uri == null) return null;
            try (OutputStream out = getContentResolver().openOutputStream(uri)) {
                if (out == null) return null;
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 92, out)) return null;
            }
            return uri;
        } catch (Exception e) { e.printStackTrace(); return null; }
    }

    private void toast(String msg) { Toast.makeText(this, msg, Toast.LENGTH_SHORT).show(); }
}
