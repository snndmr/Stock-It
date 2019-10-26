package com.snn.stockit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class CustomRoomAdapter extends RecyclerView.Adapter<CustomRoomAdapter.Holder> implements Filterable {

    private Context context;
    private ArrayList<Room> rooms;
    private CustomFilter customFilter;
    private LayoutInflater layoutInflater;

    CustomRoomAdapter(Context context) {
        this.context = context;
        this.rooms = Room.getRooms();
        this.layoutInflater = LayoutInflater.from(context);
        this.customFilter = new CustomFilter(this);
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
        return rooms.size();
    }

    @Override
    public Filter getFilter() {
        return customFilter;
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
            tv_room_card.setText(rooms.get(position).getRoom_name());
            iv_room_card.setImageResource(rooms.get(position).getRoom_image());
        }
    }

    class CustomFilter extends Filter {
        private CustomRoomAdapter mAdapter;

        CustomFilter(CustomRoomAdapter mAdapter) {
            super();
            this.mAdapter = mAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            rooms.clear();
            final FilterResults results = new FilterResults();
            if (charSequence.length() == 0) {
                rooms.addAll(Room.getRooms());
            } else {
                final String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Room room : Room.getRooms()) {
                    if (room.getRoom_name().contains(filterPattern)) {
                        rooms.add(room);
                    }
                }
            }

            results.values = rooms;
            results.count = rooms.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            this.mAdapter.notifyDataSetChanged();
        }
    }
}
