package com.snn.stockapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomDialog.BottomDialogListener {
    private ArrayList<Room> rooms;
    private BottomDialog bottomDialog;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapterRooms customAdapterRooms;

    private ArrayList<Room> getRoomTestData() {
        for (int i = 0; i < 50; i++) {
            rooms.add(new Room(
                    i % 5 == 0 ? "Bedroom" : i % 4 == 0 ? "Living Room" : i % 3 == 0 ? "Kitchen" : "Garage",
                    i % 5 == 0 ? R.drawable.room_test_1 : i % 3 == 0 ? R.drawable.room_test_2 : i % 2 == 0 ? R.drawable.room_test_3 : R.drawable.room_test_4));
        }
        return rooms;
    }

    private void init() {
        TextView textView = findViewById(R.id.tv_app_name);
        textView.setBackground(GradientColors.TEXT_VIEW_BACKGROUND);

        rooms = new ArrayList<>();
        rooms = getRoomTestData();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog = new BottomDialog();
                bottomDialog.show(getSupportFragmentManager(), "BottomDialog");
            }
        });

        RecyclerView recyclerView = findViewById(R.id.rv_rooms);
        customAdapterRooms = new CustomAdapterRooms(MainActivity.this, rooms);
        recyclerView.setAdapter(customAdapterRooms);
        gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    public void cameraClick() {

    }

    @Override
    public void addClick(String name) {
        bottomDialog.dismiss();

        rooms.add(0, new Room(name, R.drawable.room_test_3));
        customAdapterRooms.notifyDataSetChanged();
        gridLayoutManager.scrollToPositionWithOffset(0, 0);
    }
}
