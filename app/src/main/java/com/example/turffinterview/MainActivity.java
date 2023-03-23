package com.example.turffinterview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.PopupWindow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private final List<String> itemList = new ArrayList<>();
    private final List<String> userList = new ArrayList<>();
    private List<Button> userButtonList = new ArrayList<>();
    private Timer timer;
    private TimerTask autoSubmit;
    private String curItem;
    private Queue<String> submitQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submitQueue = new LinkedList<>();

        for(int i = 0; i < 1000; i++){
            itemList.add("item " + (i + 1));
            userList.add("user " + (i + 1));
        }

        curItem = itemList.get(0);
        timer = new Timer();

        RecyclerView itemListView = findViewById(R.id.itemList);
        LinearLayoutManager itemLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        itemListView.setLayoutManager(itemLayoutManager);
        ItemButtonListAdapter itemAdapter = new ItemButtonListAdapter(this);
        itemListView.setAdapter(itemAdapter);

        RecyclerView userListView = findViewById(R.id.userList);
        GridLayoutManager userLayoutManager = new GridLayoutManager(this, 5);
        userListView.setLayoutManager(userLayoutManager);
        UserButtonListAdapter userAdapter = new UserButtonListAdapter(this);
        userListView.setAdapter(userAdapter);
        userButtonList = userAdapter.getButtonList();

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("writing");
                if (submitQueue.isEmpty()) return;
                writeJSON(submitQueue.poll());
            }
        }, 0, 10000);
    }

    public void resetCounters() {
        JSONArray data = new JSONArray();
        for (int i = 0; i < userButtonList.size(); i++) {
            Button button = userButtonList.get(i);
            String text = button.getText().toString();
            int counter = Integer.parseInt(text.substring(text.indexOf("(") + 1, text.indexOf(")")));
            String name = text.split(" ")[0] + ' ' + text.split(" ")[1];
            button.setText(name + " (0)");
            try {
                JSONObject object = new JSONObject();
                object.put("name", name);
                object.put("number", counter);
                data.put(object);
            } catch (JSONException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        submitQueue.add(data.toString());
        System.out.println(data.toString());
    }

    public void startSubmitTimer() {
        if (autoSubmit != null) autoSubmit.cancel();
        autoSubmit = new TimerTask() {
            @Override
            public void run() {
                resetCounters();
            }
        };
        timer.schedule(autoSubmit, 5000);
    }

    public void writeJSON(String data) {
        try {
            // Create file
            String dir = Environment.getExternalStorageDirectory() + "/Documents/";
            FileWriter file = new FileWriter(dir + curItem + ".json");

            // Write data to file
            file.write(data);
            file.close();
            System.out.println("write " + curItem);
            System.out.println("Successfully created JSON file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void readJSON() {

    }

    public List<String> getItemList() {
        return itemList;
    }

    public List<String> getUserList() {
        return userList;
    }

    public void setCurItem(String item) {
        curItem = item;
    }
}