package com.example.lab3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddNewItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.add_new_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_new_item), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onAddPressed(View view){
        EditText nameField = findViewById(R.id.nameField);
        EditText descriptionField = findViewById(R.id.descriptionField);
        EditText deadlineField = findViewById(R.id.deadlineField);

        TodoItem td = new TodoItem(nameField.getText().toString(),descriptionField.getText().toString(),deadlineField.getText().toString());

        SharedPreferences sharedPreferences = getSharedPreferences("Todos", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("count", sharedPreferences.getInt("count",0)+1);

        editor.putString("name" + sharedPreferences.getInt("count",0), nameField.getText().toString());
        editor.putString("desc" + sharedPreferences.getInt("count",0), descriptionField.getText().toString());
        editor.putString("dead" + sharedPreferences.getInt("count",0), deadlineField.getText().toString());
        editor.apply();

        Log.d("MainActivity","did it");

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);


    }
    public void testKVStore(){
        SharedPreferences sharedPreferences = getSharedPreferences("Todos", MODE_PRIVATE);
        int count = sharedPreferences.getInt("count",-1);

        Log.d("MainActivity", String.valueOf(count));
        Log.d("MainActivity",sharedPreferences.getString("name1", "nothing"));
        for (int i = 0; i < count; i++) {
            Log.d("MainActivity",sharedPreferences.getString("name"+i, ""));
        }
    }
}
