package com.example.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);

        // Get the data from the intent
        String title = getIntent().getStringExtra("EXTRA_TITLE");
        String description = getIntent().getStringExtra("EXTRA_DESC");
        int imageResId = getIntent().getIntExtra("EXTRA_IMAGE", 0); // Default value is 0 for image ID

        // Find the views
        TextView detailTitle = findViewById(R.id.detailTitle);
        TextView detailDesc = findViewById(R.id.detailDesc);
        ImageView detailImage = findViewById(R.id.detailImage);

        // Set data to views
        detailTitle.setText(title);
        detailDesc.setText(description);
        detailImage.setImageResource(imageResId);
    }

    
}