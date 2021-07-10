package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Selection extends AppCompatActivity {
    private TextView txt_city;
    private TextView temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        txt_city=(TextView)findViewById(R.id.txt_city);
        temperature=(TextView)findViewById(R.id.txt_temp);

        txt_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openDialog(); }
        });

        temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openDialog2(); }
        });

    }

    private void openDialog() {
       dialog exampledialog = new dialog();
       exampledialog.show(getSupportFragmentManager(),"example dialog");
    }
    public void openDialog2(){
        DialogTemp ExampledialogTemp = new DialogTemp();
        ExampledialogTemp.show(getSupportFragmentManager(),"example dialog hello");
    }
}