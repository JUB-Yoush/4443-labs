package com.example.lab3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;

    @SuppressLint("MissingInflatedId")
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

        recyclerView = findViewById(R.id.recylcerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        SharedPreferences sharedPreferences = getSharedPreferences("Todos", MODE_PRIVATE);
        int count = sharedPreferences.getInt("count",0);


        adapter = new MyAdapter(getValues("name"),getValues("desc"),getValues("dead"),this);
        recyclerView.setAdapter(adapter);
    }

    public List<String> getValues(String key){
        List<String> out = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("Todos", MODE_PRIVATE);
        int count = sharedPreferences.getInt("count",0);
        for (int i = 0; i < count; i++) {
            out.add(sharedPreferences.getString(key+i,""));
        }
        Log.d("MainActivity",out.toString());
        return out;
    }

    public void addNewButtonPressed(View view){
    Intent i = new Intent(this, AddNewItemActivity.class);
    startActivity(i);
    }

    public void deleteTodo(int index){
        SharedPreferences sharedPreferences = getSharedPreferences("Todos", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
    }
}