package com.snn.stockit;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RoomActivity extends AppCompatActivity implements BottomDialog.BottomDialogListener {

    private Toast toast;
    private int room_position;
    private CustomItemAdapter customItemAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        room_position = getIntent().getIntExtra("Position", 0);
        init(room_position);
    }

    private void init(final int position) {

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog(position);
            }
        });

        ImageView imageView = findViewById(R.id.iv_room);
        imageView.setImageResource(Room.getRooms().get(position).getRoom_image());
        TextView textView = findViewById(R.id.tv_room_name);
        textView.setText(Room.rooms.get(position).getRoom_name());

        RecyclerView recyclerView = findViewById(R.id.rv_items);
        customItemAdapter = new CustomItemAdapter(RoomActivity.this, position);
        recyclerView.setAdapter(customItemAdapter);
        linearLayoutManager = new LinearLayoutManager(RoomActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void showToast(String text) {
        if (this.toast != null) {
            this.toast.cancel();
        }
        this.toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        this.toast.show();
    }

    void showAddDialog(final int position) {
        Window window = RoomActivity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(new Rect());

        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.item_dialog, viewGroup, false);

        final AlertDialog.Builder builder = new AlertDialog.Builder(RoomActivity.this, R.style.CustomAlertDialog);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        final EditText et_item_name = dialogView.findViewById(R.id.et_item_name);
        final EditText et_item_piece = dialogView.findViewById(R.id.et_item_piece);
        final EditText et_item_location = dialogView.findViewById(R.id.et_item_location);
        final EditText et_item_description = dialogView.findViewById(R.id.et_item_description);

        final TextView tv_item_add = dialogView.findViewById(R.id.tv_item_add);
        final ImageView iv_item_camera = dialogView.findViewById(R.id.iv_item_camera);

        tv_item_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_item_name.length() > 0) {
                    alertDialog.dismiss();
                    Room.getRooms().get(position).getItems().add(0, new Item(
                                    et_item_piece.length() > 0 ? Integer.valueOf(et_item_piece.getText().toString()) : 1,
                                    R.drawable.room_test_1,
                                    et_item_name.getText().toString(),
                                    et_item_location.length() > 0 ? et_item_location.getText().toString() : "Konum bilgisi yok",
                                    et_item_name.getText().toString(),
                                    et_item_description.length() > 0 ? et_item_description.getText().toString() : "Tanım bilgisi yok"
                            )
                    );

                    customItemAdapter.notifyItemInserted(0);
                    customItemAdapter.notifyItemRangeChanged(0, Room.getRooms().get(room_position).getItems().size());
                    linearLayoutManager.scrollToPositionWithOffset(0, 0);
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    MainActivity.saveData();
                } else {
                    showToast("Eşyanın adını girmelisin");
                }
            }
        });

        iv_item_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Bu özellik üzerinde çalışıyoruz");
            }
        });

        alertDialog.show();
    }

    void showEditDialog(final int position) {
        Window window = RoomActivity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(new Rect());

        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.item_dialog, viewGroup, false);

        final AlertDialog.Builder builder = new AlertDialog.Builder(RoomActivity.this, R.style.CustomAlertDialog);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        final EditText et_item_name = dialogView.findViewById(R.id.et_item_name);
        et_item_name.setHint(Room.getRooms().get(room_position).getItems().get(position).getItem_name());
        final EditText et_item_piece = dialogView.findViewById(R.id.et_item_piece);
        et_item_piece.setHint(String.valueOf(Room.getRooms().get(room_position).getItems().get(position).getItem_piece()));
        final EditText et_item_location = dialogView.findViewById(R.id.et_item_location);
        et_item_location.setHint(Room.getRooms().get(room_position).getItems().get(position).getItem_location());
        final EditText et_item_description = dialogView.findViewById(R.id.et_item_description);
        et_item_description.setHint(Room.getRooms().get(room_position).getItems().get(position).getItem_description());

        final TextView tv_item_add = dialogView.findViewById(R.id.tv_item_add);
        tv_item_add.setText(getString(R.string.update));
        final ImageView iv_item_camera = dialogView.findViewById(R.id.iv_item_camera);

        tv_item_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = false;

                if (et_item_name.length() > 0 && !Room.getRooms().get(room_position).getItems().get(position).getItem_name().equals(et_item_name.getText().toString())) {
                    Room.getRooms().get(room_position).getItems().get(position).setItem_name(et_item_name.getText().toString());
                    flag = true;
                }

                if (et_item_piece.length() > 0 && Room.getRooms().get(room_position).getItems().get(position).getItem_piece() != Integer.valueOf(et_item_piece.getText().toString())) {
                    Room.getRooms().get(room_position).getItems().get(position).setItem_piece(Integer.valueOf(et_item_piece.getText().toString()));
                    flag = true;
                }

                if (et_item_location.length() > 0 && !Room.getRooms().get(room_position).getItems().get(position).getItem_location().equals(et_item_location.getText().toString())) {
                    Room.getRooms().get(room_position).getItems().get(position).setItem_image_path(et_item_location.getText().toString());
                    flag = true;
                }

                if (et_item_description.length() > 0 && !Room.getRooms().get(room_position).getItems().get(position).getItem_description().equals(et_item_description.getText().toString())) {
                    Room.getRooms().get(room_position).getItems().get(position).setItem_description(et_item_description.getText().toString());
                    flag = true;
                }

                if (flag) {
                    customItemAdapter.notifyItemChanged(position);
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    MainActivity.saveData();
                } else {
                    showToast("Herhangi bir değişiklik yapılmadı");
                }

                alertDialog.dismiss();
            }
        });

        iv_item_camera.setOnClickListener(new View.OnClickListener() {
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
        Room.getRooms().get(room_position).getItems().remove(position);
        customItemAdapter.notifyItemRemoved(position);
        customItemAdapter.notifyItemRangeChanged(position, Room.getRooms().get(room_position).getItems().size());
        MainActivity.saveData();
    }
}
