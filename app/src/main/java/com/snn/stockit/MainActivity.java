package com.snn.stockit;

//SNN

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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomDialog.BottomDialogListener {

    private static SharedPreferences sharedPreferences;
    private Toast toast;
    private CustomRoomAdapter customRoomAdapter;
    private GridLayoutManager gridLayoutManager;

    static void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String rooms_string = new Gson().toJson(Room.getRooms());

        editor.putString("Rooms", rooms_string);
        editor.apply();
    }

    static void loadData() {
        String rooms_string = sharedPreferences.getString("Rooms", "");

        if (!rooms_string.isEmpty()) {
            Type type = new TypeToken<List<Room>>() {
            }.getType();
            Room.setRooms((ArrayList<Room>) new Gson().fromJson(rooms_string, type));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        this.customRoomAdapter = new CustomRoomAdapter(this);
        this.gridLayoutManager = new GridLayoutManager(this, 2);

        RecyclerView rv_rooms = this.findViewById(R.id.rv_rooms);
        rv_rooms.setAdapter(customRoomAdapter);
        rv_rooms.setLayoutManager(gridLayoutManager);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });

        MainActivity.loadData();
    }

    private void showToast(String text) {
        if (this.toast != null) {
            this.toast.cancel();
        }
        this.toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        this.toast.show();
    }

    void showAddDialog() {
        Window window = MainActivity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(new Rect());

        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.room_dialog, viewGroup, false);

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.CustomAlertDialog);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        final EditText et_room_dialog = dialogView.findViewById(R.id.et_room_dialog);
        final TextView tv_room_dialog = dialogView.findViewById(R.id.tv_room_dialog);
        final ImageView iv_room_dialog = dialogView.findViewById(R.id.iv_room_dialog);

        tv_room_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_room_dialog.length() > 0) {
                    alertDialog.dismiss();

                    Room.getRooms().add(0, new Room(
                            R.drawable.room_test_1, et_room_dialog.getText().toString(),
                            et_room_dialog.getText().toString(), new ArrayList<Item>())
                    );

                    customRoomAdapter.notifyItemInserted(0);
                    customRoomAdapter.notifyItemRangeChanged(0, Room.getRooms().size());
                    gridLayoutManager.scrollToPositionWithOffset(0, 0);
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    MainActivity.saveData();
                } else {
                    showToast("Odanın adını girmelisin");
                }
            }
        });

        iv_room_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Bu özellik üzerinde çalışıyoruz");
            }
        });

        alertDialog.show();
    }

    void showEditDialog(final int position) {
        Window window = MainActivity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(new Rect());

        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.room_dialog, viewGroup, false);

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.CustomAlertDialog);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        final EditText et_room_dialog = dialogView.findViewById(R.id.et_room_dialog);
        et_room_dialog.setHint(Room.getRooms().get(position).getRoom_name());
        final TextView tv_room_dialog = dialogView.findViewById(R.id.tv_room_dialog);
        tv_room_dialog.setText(getString(R.string.update));
        final ImageView iv_room_dialog = dialogView.findViewById(R.id.iv_room_dialog);

        tv_room_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Room.getRooms().get(position).getRoom_name().equals(et_room_dialog.getText().toString())) {
                    alertDialog.dismiss();
                    Room.getRooms().get(position).setRoom_name(et_room_dialog.getText().toString());
                    customRoomAdapter.notifyItemChanged(position);
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    MainActivity.saveData();
                }
            }
        });

        iv_room_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Bu özellik üzerinde çalışıyoruz");
            }
        });

        alertDialog.show();
    }

    @Override
    public void editItem(int position) {
        showEditDialog(position);
    }

    @Override
    public void deleteItem(int position) {
        Room.getRooms().remove(position);
        customRoomAdapter.notifyItemRemoved(position);
        customRoomAdapter.notifyItemRangeChanged(position, Room.getRooms().size());
        MainActivity.saveData();
    }
}
