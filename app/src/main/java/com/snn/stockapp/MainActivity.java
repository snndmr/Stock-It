package com.snn.stockapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomDialog.BottomDialogListener {

    private Toast toast;
    private ArrayList<Room> rooms;
    private SharedPreferences sharedPreferences;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapterRooms customAdapterRooms;

    private void showToast(String text) {
        if (this.toast != null) {
            this.toast.cancel();
        }
        this.toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        this.toast.show();
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String rooms_string = new Gson().toJson(rooms);

        editor.putString("Rooms", rooms_string);
        editor.apply();
    }

    private void loadData() {
        String rooms_string = sharedPreferences.getString("Rooms", "");

        if (!rooms_string.isEmpty()) {
            Type type = new TypeToken<List<Room>>() {
            }.getType();
            List<Room> rooms = new Gson().fromJson(rooms_string, type);
            this.rooms.addAll(rooms);
        }
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
                    saveData();

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
                    saveData();

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
        TextView textView = findViewById(R.id.tv_app_name);

        rooms = new ArrayList<>();
        this.sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        loadData();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog(view);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == CustomAdapterRooms.REQUEST_CODE) {
            ArrayList<Item> items;
            if (data != null) {
                items = (ArrayList<Item>) data.getSerializableExtra("Result");
                rooms.get(data.getIntExtra("Position", 0)).setItems(items);
                saveData();
                Log.e("FAK", "Test");
            }
        }
    }

    @Override
    public void editItem(int position) {
        showEditDialog(findViewById(android.R.id.content), position);
        customAdapterRooms.notifyDataSetChanged();
        saveData();
    }

    @Override
    public void deleteItem(int position) {
        rooms.remove(position);
        customAdapterRooms.notifyDataSetChanged();
        saveData();
    }
}
