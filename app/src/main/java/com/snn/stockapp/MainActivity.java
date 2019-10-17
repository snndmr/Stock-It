package com.snn.stockapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Room> getRoomTestData() {
        ArrayList<Room> rooms = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            rooms.add(new Room(
                    i % 5 == 0 ? "Bedroom" : i % 4 == 0 ? "Living Room" : i % 3 == 0 ? "Kitchen" : "Garage",
                    i % 5 == 0 ? R.drawable.room_test_1 : i % 3 == 0 ? R.drawable.room_test_2 : i % 2 == 0 ? R.drawable.room_test_3 : R.drawable.room_test_4));
        }
        return rooms;
    }

    private void init() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomDialog bottomDialog = new BottomDialog();
                bottomDialog.show(getSupportFragmentManager(), "BottomDialog");
            }
        });

        RecyclerView recyclerView = findViewById(R.id.rv_rooms);
        recyclerView.setAdapter(new CustomAdapterRooms(MainActivity.this, getRoomTestData()));
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }
}
