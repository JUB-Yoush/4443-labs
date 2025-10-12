package com.example.lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private final List<String> names;
    private final List<String> descriptions;
    private final List<String> deadlines;
    private final Context context; // add context reference

    public static class Item {
        String name;
        String description;
        String deadline;

        public Item(String name, String description, String deadline) {
            this.name = name;
            this.description = description;
            this.deadline = deadline;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView nameView;
        public final TextView descriptionView;
        public final TextView  deadlineView;
        public final TextView  indexView;


        public ViewHolder(View view) {
            super(view);
            this.nameView = view.findViewById(R.id.nameView);
            this.deadlineView = view.findViewById(R.id.deadlineView);
            this.descriptionView = view.findViewById(R.id.descriptionView);
            this.indexView = view.findViewById(R.id.indexView);
        }
    }

    public MyAdapter(List<String> names, List<String> descriptions, List<String> deadlines, Context context) {
        this.names = names;
        this.descriptions = descriptions;
        this.deadlines = deadlines;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_in_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        String name = names.get(position);
        String description = descriptions.get(position);
        String deadline = deadlines.get(position);
        String index = String.valueOf(position);

        holder.nameView.setText(name);
        holder.deadlineView.setText(description);
        holder.descriptionView.setText(deadline);
        holder.indexView.setText(index);

        // Set click listener on the whole item
        /*
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("EXTRA_TITLE", currentItem.title);
            intent.putExtra("EXTRA_DESC", currentItem.summary);
            intent.putExtra("EXTRA_IMAGE", currentItem.imageResId);
            context.startActivity(intent);
        });
         */
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    /*
    @Override
    public int getItemCount() {
        return items.size();
    }
    */
}