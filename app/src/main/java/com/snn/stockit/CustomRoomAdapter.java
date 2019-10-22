package com.snn.stockit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class CustomRoomAdapter extends RecyclerView.Adapter<CustomRoomAdapter.Holder> {
    private Context context;
    private LayoutInflater layoutInflater;

    CustomRoomAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CustomRoomAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(layoutInflater.inflate(R.layout.room_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomRoomAdapter.Holder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return Room.getRooms().size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private int position;
        private TextView tv_room_card;
        private ImageView iv_room_card;

        Holder(@NonNull View itemView) {
            super(itemView);

            tv_room_card = itemView.findViewById(R.id.tv_room_card);
            iv_room_card = itemView.findViewById(R.id.iv_room_card);
            ImageView iv_room_menu = itemView.findViewById(R.id.iv_room_menu);

            iv_room_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BottomDialog bottomDialog = new BottomDialog(position);
                    bottomDialog.show(((MainActivity) context).getSupportFragmentManager(), "BottomDialog");
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent room = new Intent(context, RoomActivity.class);
                    room.putExtra("Position", position);
                    context.startActivity(room);
                }
            });
        }

        void setData(int position) {
            this.position = position;
            tv_room_card.setText(Room.getRooms().get(position).getRoom_name());
            iv_room_card.setImageResource(Room.getRooms().get(position).getRoom_image());
        }
    }
}
