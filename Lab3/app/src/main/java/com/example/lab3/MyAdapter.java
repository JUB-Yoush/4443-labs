package com.example.lab3;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> names;
    private List<String> descriptions;
    private List<String> deadlines;
    private Context context; // add context reference

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

    // Create a GestureDetector
        GestureDetector gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                // long press
                showPopupMenu(holder.itemView, Integer.parseInt(index),name,description,deadline);
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                // tap
                Intent intent = new Intent(context, TapView.class);
                intent.putExtra("EXTRA_NAME", name);
                intent.putExtra("EXTRA_DESC", description);
                intent.putExtra("EXTRA_DEAD", deadline);
                intent.putExtra("EXTRA_INDEX", index);
                context.startActivity(intent);
                return false; // Return false to allow other clicks to be handled
            }
            // Set touch listener on the whole item view
        });
        // Set touch listener on the whole item view
        holder.itemView.setOnTouchListener((v, event) -> {
            gestureDetector.onTouchEvent(event);
            return true; // Indicate that the touch event is handled
        });
    }

    private void showPopupMenu(View view, int index, String name, String description, String deadline) {
        PopupMenu popup = new PopupMenu(context, view);
        popup.inflate(R.menu.popup_menu);

        // Set item click listeners
        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.option_one) {
                Log.d("MainActivity", "update"+index);
                //send to update page
                Intent intent = new Intent(context, TapView.class);
                intent.putExtra("EXTRA_NAME", name);
                intent.putExtra("EXTRA_DESC", description);
                intent.putExtra("EXTRA_DEAD", deadline);
                intent.putExtra("EXTRA_INDEX", index);
                context.startActivity(intent);
                return true;
            }
            if (item.getItemId() == R.id.option_two) {
                Log.d("MainActivity","delete"+index);
                deleteTodo(index);
                return true;
            }
            return false;
        });

        popup.show(); // Display the popup menu
    }
    @Override
    public int getItemCount() {
        return names.size();
    }

    private void deleteTodo(int index){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Todos", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int len = sharedPreferences.getInt("count",0);

        // remove item
        editor.putString("name"+index,"");
        editor.putString("desc"+index,"");
        editor.putString("dead"+index,"");

        // shift down values (list is 1 indexed)
        for (int i = index+1; i <=len; i++) {
            editor.putString("name"+(i-1),sharedPreferences.getString("name"+i,""));
            editor.putString("desc"+(i-1),sharedPreferences.getString("desc"+i,""));
            editor.putString("dead"+(i-1),sharedPreferences.getString("dead"+i,""));
        }
        editor.putInt("count",len-1);

        editor.putString("name"+len,"");
        editor.putString("desc"+len,"");
        editor.putString("dead"+len,"");

        names = getValues("name");
        descriptions= getValues("desc");
        deadlines = getValues("dead");

        editor.apply();
        notifyDataSetChanged();
        notifyItemRemoved(index);
    }
    public List<String> getValues(String key){
        List<String> out = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("Todos", MODE_PRIVATE);
        int count = sharedPreferences.getInt("count",0);
        for (int i = 0; i < count; i++) {
            out.add(sharedPreferences.getString(key+i,""));
        }
        Log.d("MainActivity",out.toString());
        return out;
    }


}