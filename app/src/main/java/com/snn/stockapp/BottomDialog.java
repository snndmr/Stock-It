package com.snn.stockapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomDialog extends BottomSheetDialogFragment {
    private BottomDialogListener bottomDialogListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.room_dialog, container, false);

        final EditText editText = view.findViewById(R.id.et_room_add);
        TextView textView = view.findViewById(R.id.tv_room_add);
        ImageView imageView = view.findViewById(R.id.iv_camera);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialogListener.addClick(editText.getText().toString());
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialogListener.cameraClick();
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            bottomDialogListener = (BottomDialogListener) context;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    interface BottomDialogListener {
        void cameraClick();

        void addClick(String name);
    }
}
