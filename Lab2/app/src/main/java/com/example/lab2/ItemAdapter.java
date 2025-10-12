package com.example.lab2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final List<Item> items;
    private final Context context;

    public static class Item {
        String title;
        String summary;
        int imageResId;

        public Item(String title, String summary, int imageResId) {
            this.title = title;
            this.summary = summary;
            this.imageResId = imageResId;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imgThumb;
        public final TextView txtTitle;
        public final TextView txtSummary;

        public ViewHolder(View view) {
            super(view);
            imgThumb = view.findViewById(R.id.imgThumb);
            txtTitle = view.findViewById(R.id.txtTitle);
            txtSummary = view.findViewById(R.id.txtSummary);
        }
    }

    public ItemAdapter(List<Item> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        Item currentItem = items.get(position);
        holder.txtTitle.setText(currentItem.title);
        holder.txtSummary.setText(currentItem.summary);

        // Set the image resource
        holder.imgThumb.setImageResource(currentItem.imageResId);

        // Set click listener to go to detail activity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("EXTRA_TITLE", currentItem.title);
            intent.putExtra("EXTRA_DESC", currentItem.summary);
            intent.putExtra("EXTRA_IMAGE", currentItem.imageResId);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
