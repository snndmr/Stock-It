package com.snn.stockapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class CustomAdapterRooms extends RecyclerView.Adapter<CustomAdapterRooms.Holder> {

    private final int REQUEST_CODE = 10;
    private Context context;
    private ArrayList<Room> rooms;
    private LayoutInflater layoutInflater;

    CustomAdapterRooms(Context context, ArrayList<Room> roomTestData) {
        this.context = context;
        this.rooms = roomTestData;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(layoutInflater.inflate(R.layout.room_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private int position;
        private TextView textView;
        private ImageView imageView;
        private ImageView imageViewMenu;

        Holder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_room_card);
            imageView = itemView.findViewById(R.id.iv_room_card);
            imageViewMenu = itemView.findViewById(R.id.iv_room_menu);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, RoomActivity.class);
                    intent.putExtra("Position", position);
                    intent.putExtra("Room", rooms.get(position));
                    ((Activity) context).startActivityForResult(intent, REQUEST_CODE);
                }
            });

            imageViewMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }

        void setData(int position) {
            this.position = position;

            textView.setText(rooms.get(position).getName());
            textView.setBackground(GradientColors.TEXT_VIEW_BACKGROUND);
            Picasso.get().load(rooms.get(position).getImage()).fit().centerCrop().into(imageView);
        }
    }
}
