package com.snn.stockapp;

import android.app.Activity;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Objects;

public class RoomActivity extends AppCompatActivity implements BottomDialog.BottomDialogListener {
    private Toast toast;
    private ArrayList<Item> items;
    private CustomAdapterItems customAdapterItems;
    private LinearLayoutManager linearLayoutManager;

    private void showToast(String text) {
        if (this.toast != null) {
            this.toast.cancel();
        }
        this.toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        this.toast.show();
    }

    void showAddDialog(View view, final int position) {
        Window window = RoomActivity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(new Rect());

        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.item_dialog, viewGroup, false);

        final AlertDialog.Builder builder = new AlertDialog.Builder(RoomActivity.this, R.style.CustomAlertDialog);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        final EditText editTextName = dialogView.findViewById(R.id.et_item_name);
        final EditText editTextPiece = dialogView.findViewById(R.id.et_item_piece);
        final EditText editTextLocation = dialogView.findViewById(R.id.et_item_location);
        final EditText editTextDescription = dialogView.findViewById(R.id.et_item_description);

        final TextView textViewAdd = dialogView.findViewById(R.id.tv_room_add);
        final ImageView imageViewCamera = dialogView.findViewById(R.id.iv_camera);

        textViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextName.length() > 0) {
                    alertDialog.dismiss();
                    items.add(0,
                            new Item(editTextName.getText().toString(),
                                    editTextLocation.length() > 0 ? editTextLocation.getText().toString() : "Yer belirtilmedi.",
                                    editTextPiece.length() > 0 ? Integer.valueOf(editTextPiece.getText().toString()) : 1,
                                    editTextDescription.length() > 0 ? editTextPiece.getText().toString() : "Tanım yapılmadı."));

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("Result", items);
                    returnIntent.putExtra("Position", position);
                    setResult(Activity.RESULT_OK, returnIntent);

                    customAdapterItems.notifyDataSetChanged();
                    linearLayoutManager.scrollToPositionWithOffset(0, 0);

                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    showToast("Eşya eklendi");
                } else {
                    showToast("Eşyanın adını girmelisin");
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
        Window window = RoomActivity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(new Rect());

        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.item_bottom_edit_dialog, viewGroup, false);

        final AlertDialog.Builder builder = new AlertDialog.Builder(RoomActivity.this, R.style.CustomAlertDialog);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        final EditText editTextName = dialogView.findViewById(R.id.et_item_name);
        final EditText editTextPiece = dialogView.findViewById(R.id.et_item_piece);
        final EditText editTextLocation = dialogView.findViewById(R.id.et_item_location);
        final EditText editTextDescription = dialogView.findViewById(R.id.et_item_description);

        final TextView textViewAdd = dialogView.findViewById(R.id.tv_room_add);
        final ImageView imageViewCamera = dialogView.findViewById(R.id.iv_camera);

        textViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextName.length() > 0) {
                    alertDialog.dismiss();
                    items.set(position,
                            new Item(editTextName.getText().toString(),
                                    editTextLocation.length() > 0 ? editTextLocation.getText().toString() : "Yer belirtilmedi.",
                                    editTextPiece.length() > 0 ? Integer.valueOf(editTextPiece.getText().toString()) : 1,
                                    editTextDescription.length() > 0 ? editTextPiece.getText().toString() : "Tanım yapılmadı."));

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("Result", items);
                    returnIntent.putExtra("Position", position);
                    setResult(Activity.RESULT_OK, returnIntent);

                    customAdapterItems.notifyDataSetChanged();
                    linearLayoutManager.scrollToPositionWithOffset(0, 0);

                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    showToast("Eşya güncellendi");
                } else {
                    showToast("Eşyanın adını girmelisin");
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

    private void init(Room room, final int position) {
        items = room.getItems();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog(view, position);
            }
        });

        ImageView imageView = findViewById(R.id.iv_room);
        imageView.setImageResource(room.getImage());
        TextView textView = findViewById(R.id.tv_room_name);
        textView.setText(room.getName());

        RecyclerView recyclerView = findViewById(R.id.rv_items);
        customAdapterItems = new CustomAdapterItems(RoomActivity.this, items);
        recyclerView.setAdapter(customAdapterItems);
        linearLayoutManager = new LinearLayoutManager(RoomActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        getWindow().getDecorView().setBackground(GradientColors.BACKGROUND);
        init((Room) Objects.requireNonNull(getIntent().getSerializableExtra("Room")),
                getIntent().getIntExtra("Position", 0));
    }

    @Override
    public void editItem(int position) {
        showEditDialog(findViewById(android.R.id.content), position);
    }

    @Override
    public void deleteItem(int position) {
        items.remove(position);

        Intent returnIntent = new Intent();
        returnIntent.putExtra("Result", items);
        returnIntent.putExtra("Position", position);
        setResult(Activity.RESULT_OK, returnIntent);

        customAdapterItems.notifyDataSetChanged();
        linearLayoutManager.scrollToPositionWithOffset(0, 0);
    }
}
