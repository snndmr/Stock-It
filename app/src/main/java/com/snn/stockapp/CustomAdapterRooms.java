package com.snn.stockapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class CustomAdapterRooms extends RecyclerView.Adapter<CustomAdapterRooms.Holder> {

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

        Holder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_room_card);
            imageView = itemView.findViewById(R.id.iv_room_card);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, RoomActivity.class);
                    intent.putExtra("Room", rooms.get(position));
                    context.startActivity(intent);
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