package com.example.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

    TextView nameView = findViewById(R.id.nameView1);
    TextView descView = findViewById(R.id.descView1);
    TextView deadView = findViewById(R.id.deadView1);

    nameView.setText(name);
    descView.setText(desc);
    deadView.setText(deadline);
    }

    public void returnPressed(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}