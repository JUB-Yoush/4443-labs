package com.example.lab4;

import android.net.Uri;
import android.view,LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.annotation.NonNull;
import android.receyleview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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
    }
}

pulic class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.VH>{
    private final List<Uri> items = new ArrayList<>();

    static class VH extends RecyclerView.ViewHolder {
        ImageView img;
        VH(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
        }
}