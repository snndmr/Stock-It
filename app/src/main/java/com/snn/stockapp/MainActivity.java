package com.snn.stockapp;

import android.content.SharedPreferences;
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

public class MainActivity extends AppCompatActivity implements BottomDialog.BottomDialogListener {

    static SharedPreferences sharedPreferences;
    private Toast toast;
    private ArrayList<Room> rooms;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapterRooms customAdapterRooms;

    private void showToast(String text) {
        if (this.toast != null) {
            this.toast.cancel();
        }
        this.toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        this.toast.show();
    }

    void showAddDialog(View view) {
        Window window = MainActivity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(new Rect());

        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.room_add_dialog, viewGroup, false);

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.CustomAlertDialog);
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
                    rooms.add(0, new Room(editTextAdd.getText().toString()));
                    Room.saveData();

                    customAdapterRooms.notifyDataSetChanged();
                    gridLayoutManager.scrollToPositionWithOffset(0, 0);

                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    showToast("Oda eklendi");
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

    void showEditDialog(View view, final int position) {
        Window window = MainActivity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(new Rect());

        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.room_edit_dialog, viewGroup, false);

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.CustomAlertDialog);
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
                    rooms.set(position, new Room(editTextAdd.getText().toString()));
                    Room.saveData();

                    customAdapterRooms.notifyDataSetChanged();
                    gridLayoutManager.scrollToPositionWithOffset(0, 0);

                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    showToast("Oda düzenlendi");
                } else {
                    showToast("Odanın yeni adını girmelisin");
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

    private void init() {
        //TextView textView = findViewById(R.id.tv_app_name);

        rooms = new ArrayList<>();
        MainActivity.sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        Room.rooms = (ArrayList<Room>) Room.loadData();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog(view);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.rv_rooms);
        customAdapterRooms = new CustomAdapterRooms(MainActivity.this, (ArrayList<Room>) Room.loadData());
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

    @Override
    public void editItem(int position) {
        showEditDialog(findViewById(android.R.id.content), position);
        customAdapterRooms.notifyDataSetChanged();
        Room.saveData();
    }

    @Override
    public void deleteItem(int position) {
        rooms.remove(position);
        customAdapterRooms.notifyDataSetChanged();
        Room.saveData();
    }
}
