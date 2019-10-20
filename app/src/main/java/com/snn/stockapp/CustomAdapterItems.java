package com.snn.stockapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class CustomAdapterItems extends RecyclerView.Adapter<CustomAdapterItems.Holder> {

    private Context context;
    private ArrayList<Item> items;
    private LayoutInflater layoutInflater;

    CustomAdapterItems(Context context, ArrayList<Item> itemTestData) {
        this.context = context;
        this.items = itemTestData;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(layoutInflater.inflate(R.layout.item_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private int position;
        private TextView tv_name;
        private TextView tv_piece;
        private TextView tv_location;
        private TextView tv_description;
        private ImageView imageView;

        Holder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_item_name);
            tv_piece = itemView.findViewById(R.id.tv_item_piece);
            tv_location = itemView.findViewById(R.id.tv_item_location);
            tv_description = itemView.findViewById(R.id.tv_item_description);
            imageView = itemView.findViewById(R.id.iv_item_card);

            ImageView imageViewMenu = itemView.findViewById(R.id.iv_item_menu);
            imageViewMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BottomDialog bottomDialog = new BottomDialog(position);
                    bottomDialog.show(((FragmentActivity) context).getSupportFragmentManager(), "BottomDialog");
                }
            });
        }

        void setData(int position) {
            this.position = position;
            tv_name.setText(items.get(position).getName());
            tv_location.setText(items.get(position).getLocation());
            tv_piece.setText(context.getString(R.string.piece, String.valueOf(items.get(position).getPiece())));
            tv_description.setText(items.get(position).getDescription());
            Picasso.get().load(items.get(position).getImage()).fit().centerCrop().into(imageView);
        }
    }
}
