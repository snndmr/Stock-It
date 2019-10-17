package com.snn.stockapp;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class RoomActivity extends AppCompatActivity {

    private ArrayList<Item> getItemTestData() {
        ArrayList<Item> items = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            items.add(new Item("Item Test", "Location will come here!"));
        }
        return items;
    }

    private void init(Room room) {
        ImageView imageView = findViewById(R.id.iv_room);
        imageView.setImageResource(room.getImage());

        RecyclerView recyclerView = findViewById(R.id.rv_items);
        recyclerView.setAdapter(new CustomAdapterItems(RoomActivity.this, getItemTestData()));
        recyclerView.setLayoutManager(new LinearLayoutManager(
                RoomActivity.this, RecyclerView.VERTICAL, false));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        init((Room) Objects.requireNonNull(getIntent().getSerializableExtra("Room")));
    }
}
