package com.mk.utils;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.InsetDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mk.convert.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CallbackDialog {
    static Context ctx = null;

    // Updated method to accept a callback
    public static void showMapDialog(Context context, Map<String, String> map, TextView unitName, TextView unitCode, OnItemSelectedListener callback) {
        // Create dialog
        ctx = context;
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_map);

        // Set rounded corners for the dialog
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new InsetDrawable(AppCompatResources.getDrawable(context, R.drawable.rounded_background), 0));

        // Position dialog to cover and start on the invoking TextView
        Window window = dialog.getWindow();
        if (window != null) {
            int[] location = new int[2];
            unitName.getLocationOnScreen(location);

            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.TOP | Gravity.START;
            params.x = location[0]; // Align with the TextView's X position
            params.y = location[1]; // Align with the TextView's Y position
            window.setAttributes(params);
        }

        // Initialize RecyclerView
        RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        // Set fixed size for the RecyclerView
        recyclerView.getLayoutParams().height = (int) (5 * context.getResources().getDimension(R.dimen.item_height));

        // Create and set adapter
        MapAdapter adapter = new MapAdapter(map, unitName, unitCode, dialog, callback);
        recyclerView.setAdapter(adapter);

        // Scroll to the last selected position
        if (adapter.getSelectedPosition() != -1) {
            recyclerView.scrollToPosition(adapter.getSelectedPosition());
        }

        // Show dialog
        dialog.show();
    }

    // Adapter class for RecyclerView
    static class MapAdapter extends RecyclerView.Adapter<MapAdapter.ViewHolder> {
        private final List<Map.Entry<String, String>> mapEntries;
        private final TextView unitName;
        private final TextView unitCode;
        private final Dialog dialog;
        private final OnItemSelectedListener callback; // Added callback reference
        private int selectedPosition = -1;

        public MapAdapter(Map<String, String> map, TextView unitName, TextView unitCode, Dialog dialog, OnItemSelectedListener callback) {
            this.mapEntries = new ArrayList<>(map.entrySet());
            this.unitName = unitName;
            this.unitCode = unitCode;
            this.dialog = dialog;
            this.callback = callback;
            // Initialize selected position based on current value of unitCode
            for (int i = 0; i < mapEntries.size(); i++) {
                if (mapEntries.get(i).getKey().equals(unitCode.getText().toString())) {
                    selectedPosition = i;
                    break;
                }
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_map, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Map.Entry<String, String> entry = mapEntries.get(holder.getAdapterPosition());
            holder.keyTextView.setText(entry.getKey());
            holder.valueTextView.setText(entry.getValue());

            holder.itemView.setOnClickListener(v -> {
                // Update TextViews
                unitName.setText(entry.getValue());
                unitCode.setText(entry.getKey());

                // Update selected position and refresh the list
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(unitName.getId());

                // Notify the callback
                if (callback != null) {
                    callback.onItemSelected(entry.getKey(), entry.getValue());
                }

                // Dismiss dialog
                dialog.dismiss();
            });

            // Change background color and show check for the selected item
            if (selectedPosition == holder.getAdapterPosition()) {
                holder.checkIcon.setVisibility(View.VISIBLE);
                holder.itemView.setBackground(ContextCompat.getDrawable(ctx, R.drawable.rounded_background));
                holder.itemView.setBackgroundColor(Color.parseColor("#FFE4B5")); // Pale orange
            } else {
                holder.checkIcon.setVisibility(View.INVISIBLE);
                holder.itemView.setBackgroundColor(Color.WHITE);
            }
        }

        @Override
        public int getItemCount() {
            return mapEntries.size();
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        // ViewHolder class
        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView keyTextView, valueTextView;
            ImageView checkIcon;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                keyTextView = itemView.findViewById(R.id.keyTextView);
                valueTextView = itemView.findViewById(R.id.valueTextView);
                checkIcon = itemView.findViewById(R.id.checkIcon);
            }
        }
    }

    // Callback interface
    public interface OnItemSelectedListener {
        void onItemSelected(String key, String value);
    }
}

