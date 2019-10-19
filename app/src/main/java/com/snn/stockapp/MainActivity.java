package com.snn.stockapp;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Room> rooms;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapterRooms customAdapterRooms;
    private Toast toast;

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
                showDialog(view);
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
        getWindow().getDecorView().setBackground(GradientColors.BACKGROUND);
        init();
    }

    private void showToast(String text) {
        if (this.toast != null) {
            this.toast.cancel();
        }
        this.toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        this.toast.show();
    }

    void showDialog(View view) {
        Rect displayRectangle = new Rect();
        Window window = MainActivity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.room_dialog, viewGroup, false);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        final EditText editTextAdd = dialogView.findViewById(R.id.et_room_add);
        final TextView textViewAdd = dialogView.findViewById(R.id.tv_room_add);
        final ImageView imageViewCamera = dialogView.findViewById(R.id.iv_camera);

        textViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextAdd.length() > 0) {
                    alertDialog.dismiss();
                    rooms.add(0, new Room(editTextAdd.getText().toString(), R.drawable.room_test_3));
                    customAdapterRooms.notifyDataSetChanged();
                    gridLayoutManager.scrollToPositionWithOffset(0, 0);
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                } else {
                    showToast("Odanın adını girmelisin");
                }
            }
        });

        imageViewCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Bu özellik yapım aşamasında");
            }
        });

        alertDialog.show();
    }
}
