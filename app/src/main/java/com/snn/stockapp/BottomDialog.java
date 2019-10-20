package com.snn.stockapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomDialog extends BottomSheetDialogFragment {
    private int position;
    private BottomDialogListener bottomDialogListener;

    BottomDialog(int position) {
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.room_bottom_edit_dialog, container, false);

        TextView textViewEdit = view.findViewById(R.id.dialog_edit);
        TextView textViewShare = view.findViewById(R.id.dialog_share);
        TextView textViewDelete = view.findViewById(R.id.dialog_delete);

        textViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialogListener.editItem(position);
                dismiss();
            }
        });

        textViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Buralar hep dutluktu", Toast.LENGTH_SHORT).show();
            }
        });

        textViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialogListener.deleteItem(position);
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        bottomDialogListener = (BottomDialogListener) context;
    }

    public interface BottomDialogListener {
        void editItem(int position);

        void deleteItem(int position);
    }
}
