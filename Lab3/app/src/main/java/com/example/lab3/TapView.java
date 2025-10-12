package com.example.lab3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class TapView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tap_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    String name = getIntent().getStringExtra("EXTRA_NAME");
    String desc = getIntent().getStringExtra("EXTRA_DESC");
    String deadline = getIntent().getStringExtra("EXTRA_DEAD");
    int index = getIntent().getIntExtra("EXTRA_INDEX",0);

    EditText nameView = findViewById(R.id.nameView1);
    EditText descView = findViewById(R.id.descView1);
    EditText deadView = findViewById(R.id.deadView1);

    nameView.setText(name);
    descView.setText(desc);
    deadView.setText(deadline);
    }

    public void returnPressed(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void applyChanges(View view){

        int index = getIntent().getIntExtra("EXTRA_INDEX",0);
        EditText nameView = findViewById(R.id.nameView1);
        EditText descView = findViewById(R.id.descView1);
        EditText deadView = findViewById(R.id.deadView1);

        SharedPreferences sharedPreferences = getSharedPreferences("Todos", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("name"+index,nameView.getText().toString());
        editor.putString("desc"+index,descView.getText().toString());
        editor.putString("dead"+index,deadView.getText().toString());
        editor.apply();

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);




    }
}