package com.example.weatherapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogTemp extends AppCompatDialogFragment {
    String selectedUnit= "Celsius";
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_temp, null);

        final String[] unit={"Celsius","Fahrenheit"};

        builder.setView(view);
        builder.setTitle("Temperature Unit");
        builder.setSingleChoiceItems(unit, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                selectedUnit =unit[i];
//                Intent unitSend = new Intent(getActivity(),MainActivity.class);
//                unitSend.putExtra("unit",selectedUnit);
//                startActivity(unitSend);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i){
                dialog.cancel();
            }
        });
        return builder.create();
        }
    }

