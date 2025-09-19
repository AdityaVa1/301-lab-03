package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {

    private static final String ARG_CITY = "city";
    private City cityToEdit;

    // 1. Define the listener interface
    interface EditCityDialogListener {
        void onCityEdited(); // We don't need to pass the city back
    }

    private EditCityDialogListener listener;

    // 2. newInstance pattern to pass the city object
    public static EditCityFragment newInstance(City city) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, city); // Put the city to edit in the bundle
        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Inflate the same layout as your AddCityFragment
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // 3. Get the city from the arguments
        if (getArguments() != null) {
            cityToEdit = (City) getArguments().getSerializable(ARG_CITY);
            // Pre-populate the EditText fields with the city's current data
            editCityName.setText(cityToEdit.getName());
            editProvinceName.setText(cityToEdit.getProvince());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Edit City") // Change title
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Update", (dialog, which) -> { // Change button text
                    String newCityName = editCityName.getText().toString();
                    String newProvinceName = editProvinceName.getText().toString();

                    // 4. Update the existing city object directly
                    if (cityToEdit != null) {
                        cityToEdit.setName(newCityName);
                        cityToEdit.setProvince(newProvinceName);
                    }
                    listener.onCityEdited(); // Notify the activity
                })
                .create();
    }
}
