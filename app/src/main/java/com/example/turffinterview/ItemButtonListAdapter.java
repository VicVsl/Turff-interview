package com.example.turffinterview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class ItemButtonListAdapter extends RecyclerView.Adapter<ItemButtonListAdapter.ButtonViewHolder> {

    private final MainActivity app;
    private List<Button> buttonList = new ArrayList<>();

    public ItemButtonListAdapter(MainActivity app) {
        this.app = app;
    }

    @NonNull
    @Override
    public ButtonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button, parent, false);
        return new ButtonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ButtonViewHolder holder, int position) {
        String item = app.getItemList().get(position);

        holder.button.setText(item);
        holder.button.setBackgroundColor(app.getResources().getColor(R.color.dark_orange));
        if (position == 0) holder.button.setBackgroundColor(app.getResources().getColor(R.color.orange));

        // Configures the addition of balance
        holder.button.setOnClickListener(view -> {
            for (int i = 0; i < buttonList.size(); i++) {
                buttonList.get(i).setBackgroundColor(app.getResources().getColor(R.color.dark_orange));
            }
            holder.button.setBackgroundColor(app.getResources().getColor(R.color.orange));
            app.setCurItem(item);
            app.resetCounters();
        });
        // Add button to the list
        buttonList.add(holder.button);
    }

    @Override
    public int getItemCount() {
        return app.getItemList().size();
    }

    // Inner class to hold a reference to each item of RecyclerView
    public static class ButtonViewHolder extends RecyclerView.ViewHolder {

        public final Button button;

        public ButtonViewHolder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.button);
        }
    }
}
