package com.example.turffinterview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class UserButtonListAdapter extends RecyclerView.Adapter<UserButtonListAdapter.ButtonViewHolder> {

    private final MainActivity app;
    private List<Button> buttonList = new ArrayList<>();

    public UserButtonListAdapter(MainActivity app) {
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
        String user = app.getUserList().get(position);

        holder.button.setText(user + " (0)");
        holder.button.setBackgroundColor(app.getResources().getColor(R.color.purple_200));

        // Configures the addition of balance
        holder.button.setOnClickListener(view -> {
            String text = holder.button.getText().toString();
            int counter = Integer.parseInt(text.substring(text.indexOf("(") + 1, text.indexOf(")")));
            counter++;
            holder.button.setText(user + " (" + counter + ')');
            app.startSubmitTimer();
        });
        // Add button to the list
        buttonList.add(holder.button);
    }

    @Override
    public int getItemCount() {
        return app.getUserList().size();
    }

    // Inner class to hold a reference to each item of RecyclerView
    public static class ButtonViewHolder extends RecyclerView.ViewHolder {

        public final Button button;

        public ButtonViewHolder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.button);
        }
    }

    public List<Button> getButtonList() {
        return buttonList;
    }
}
