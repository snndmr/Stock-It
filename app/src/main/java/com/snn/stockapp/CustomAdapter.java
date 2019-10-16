package com.snn.stockapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder> {

    private ArrayList<Room> rooms;
    private LayoutInflater layoutInflater;

    CustomAdapter(Context context, ArrayList<Room> roomTestData) {
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

        private TextView textView;
        private ImageView imageView;

        Holder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_room_card);
            imageView = itemView.findViewById(R.id.iv_room_card);
        }

        void setData(int position) {
            textView.setText(rooms.get(position).getName());
            textView.setBackground(GradientColors.TEXT_VIEW_BACKGROUND);
            imageView.setImageResource(rooms.get(position).getImage());
        }
    }
}
