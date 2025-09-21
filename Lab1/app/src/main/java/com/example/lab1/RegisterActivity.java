package com.example.lab1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        writeToFile("admin" + "\n",this);
        writeToFile("1234"+ "\n" ,this);
    }
    public void onLoginClicked(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void registerAccount(View view){
        EditText userInput = findViewById(R.id.userInput);
        EditText passInput = findViewById(R.id.passInput);
        writeToFile(userInput.getText().toString() + "\n",this);
        writeToFile(passInput.getText().toString() + "\n" ,this);
        Log.d("RegisterActivity","registering account");
    }
    private void writeToFile(String data, Context context) {
        // write username and password in a file
        File file = new File(context.getFilesDir(), "users.txt");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.e("Exception", "File creation failed: " + e.toString());
                return;
            }
        }
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("users.txt", Context.MODE_APPEND));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

}