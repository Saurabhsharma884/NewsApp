package com.example.newsapp;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AlertFragment extends DialogFragment {

    private final int errorType;

    public AlertFragment(int error) {
        this.errorType = error;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View errorView = inflater.inflate(R.layout.alert_layout, null);

        ImageView errorImg = errorView.findViewById(R.id.alert_image);
        TextView errorText = errorView.findViewById(R.id.alert_text);
        Button disbtn = errorView.findViewById(R.id.dismiss_button);

        if (errorType == 0) {
            errorImg.setImageResource(R.drawable.no_wifi);
            errorText.setText(R.string.no_network);

        } else {
            errorImg.setImageResource(R.drawable.error_404);
            errorText.setText(R.string.no_response);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setView(errorView);

        disbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return builder.create();
    }
}
