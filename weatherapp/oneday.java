package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class oneday extends AppCompatActivity {
    TextView Location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oneday);

        Location= (TextView)findViewById(R.id.txt_location);

        Bundle bundle = getIntent().getExtras();
        String temp = bundle.getString("tem");
        TextView txt_temp = (TextView) findViewById(R.id.txt_temp);
        txt_temp.setText(temp);

        String weat = bundle.getString("weath");
        TextView txt_weather = (TextView) findViewById(R.id.txt_weather);
        txt_weather.setText(weat);

        String humini = bundle.getString("humi");
        TextView txt_humidity = (TextView) findViewById(R.id.txt_humidity);
        txt_humidity.setText(humini);

        String date1 = bundle.getString("date");
        TextView txt_date1 = (TextView) findViewById(R.id.txt_date);
        txt_date1.setText(date1);

        String ico = bundle.getString("icon");
        ImageView img_icon = (ImageView) findViewById(R.id.img_icon);
        Picasso.get().load("https://openweathermap.org/img/w/" + ico + ".png").into(img_icon);

        String cityName = bundle.getString("city");
        TextView txt_city = (TextView) findViewById(R.id.txt_location);
        txt_city.setText(cityName);
    }

}