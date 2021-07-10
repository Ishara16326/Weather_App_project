package com.example.weatherapp;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity{
    String[] Day_listNew ={"", "", "","","","",""};
    String[] day_list = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    TextView loc;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Asyn AsynData = new Asyn();
        AsynData.execute();

        loc =(TextView)findViewById(R.id.txt_location);

//..........date................
        Date date = new Date();
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(date);

        int  start = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        int pos = 0;
        for(int a = start; a <7; a++){ Day_listNew[pos] = day_list[a];pos++; }
        for(int b = 0; b < start; b++){ Day_listNew[pos] = day_list[b];pos++; }
        for(int c = 0; c <Day_listNew.length; c++){ System.out.println(Day_listNew[c]+"\n"); }

        WeatherAdapter adapter= new WeatherAdapter(MainActivity.this,AsynData.getTempArray(),AsynData.getWeatherArray(),AsynData.getIconArArray(),Day_listNew);
        ListView weatherlist = (ListView) findViewById(R.id.list_item);
        weatherlist.setAdapter(adapter);

 //.....................Item click................................................

        weatherlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Br_temp = AsynData.getTempArray()[+position];
                String Br_weat = AsynData.getWeatherArray()[+position];
                String Br_humi = AsynData.getHumidityArray()[+position];
                String Br_icon = AsynData.getIconArArray()[+position];
                String Br_date = strDate;
                //String Br_Temp= AsynData.getTemp2Array()[+position];
                String Br_city = AsynData.getCity();
                Intent i = new Intent(MainActivity.this,oneday.class);
                Toast.makeText(getApplicationContext(),Br_temp,Toast.LENGTH_SHORT).show();

                Bundle bundle= new Bundle();
//                if(AsynData.getUnit().equals("Fahrenheit")){
//                    bundle.putString("tem",Br_Temp);
//                }
                bundle.putString("tem",Br_temp);
                bundle.putString("weath",Br_weat);
                bundle.putString("humi",Br_humi);
                bundle.putString("icon",Br_icon);
                bundle.putString("date",Br_date);
                bundle.putString("city",Br_city);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }
// ...............AsyncTask......................................................

    public class Asyn extends AsyncTask<String,Void,String> {

        HttpURLConnection urlConnection= null;
        BufferedReader reader = null;
        String forecastJsonStr="";

        String [] weatherArray = new String[7];
        String [] tempArray =new String[7];
        String [] iconArArray =new String[7];
        String[] humidityArray =new String[7];
        String[] TempArray= new String[7];
       // double[] Temp2Array= new double[7];
        String city="";
        //String unit="";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {

            Intent getcity = getIntent();
            String cityName = getcity.getStringExtra("city");
            loc.setText(cityName);

//            Intent getUnit = getIntent();
//            String Unit = getUnit.getStringExtra("unit");
//            unit= Unit;
//            if(Unit.equals("Fahrenheit")){
//                    for(int j =0; j<7;j++){
//                        Temp2Array[j] = Double.parseDouble(tempArray[j]);
//                        Temp2Array[j]= (Temp2Array[j]*9/5)+32;
//                        TempArray[j]= String.valueOf(Temp2Array[j]);
//                    }
//            }

            String first = "https://api.openweathermap.org/data/2.5/forecast/daily?q=";
            String last = "&cnt=7&appid=a18b978603316d47c572d98d52a420f6";
            String middle;

            if(loc.getText().toString().equals("") && loc.getText().length() <= 0){
                middle = first + "colombo" + last;
                city="colombo";
            } else {
                middle = first + cityName + last;
                city=cityName;
            }
            try{
                String BASE_URL = middle;
                URL url = new URL(BASE_URL);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line1;
                while ((line1 = reader.readLine()) != null) {
                    buffer.append(line1 + "\n");
                }
                if (buffer.length() == 0) { return null; }
                forecastJsonStr = buffer.toString();
                System.out.println(forecastJsonStr);

            } catch (IOException e) {
                Log.e("Hi", "Error", e);
                return null;
            } finally {
                if (urlConnection != null) { urlConnection.disconnect(); }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Hi", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        public String[] getWeatherArray(){ return weatherArray; }
        public String[] getTempArray(){ return tempArray; }
        public String[] getIconArArray(){ return iconArArray; }
        public String[] getHumidityArray(){ return humidityArray; }
        public String getCity(){return city;}
        //public String getUnit(){return unit;}
        //public String[] getTemp2Array(){ return TempArray; }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);

            try {
                JSONObject weatherdata = new JSONObject(forecastJsonStr);
                JSONArray List = weatherdata.getJSONArray("list");
                for(int i=0; i<7;i++) {
                    JSONObject day1 = List.getJSONObject(i);
                    JSONObject tempr = day1.getJSONObject("temp");
                    String humi = day1.getString("humidity");
                    int temp_val = tempr.getInt("day")-273;
                    String temp = Integer.toString(temp_val);

                    JSONArray weatherdis = day1.getJSONArray("weather");
                    JSONObject day1_weather = weatherdis.getJSONObject(0);
                    String main = day1_weather.getString("main");
                    String icon1 = day1_weather.getString("icon");

                    weatherArray[i]= main;
                    tempArray[i]= temp;
                    iconArArray[i]= icon1;
                    humidityArray[i]=humi;
                    System.out.println(forecastJsonStr);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

//...............menu bar................................................
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id ==R.id.itm_about){
            Intent intent1 = new Intent(MainActivity.this,about.class);
            startActivity(intent1);
        }
        if (id ==R.id.itm_setting){
            Intent intent2 = new Intent(MainActivity.this,Selection.class);
            startActivity(intent2);
        }
        return super.onOptionsItemSelected(item);
    }

    // .......................adapter.....................................

    public class WeatherAdapter extends ArrayAdapter<String> {
        Activity context;
        String[] temperature ;
        String[] description;
        String[] image;
        String[] weekday;

        public WeatherAdapter(Activity context,String[] temperature,String[] description,String[] image, String[] weekday) {
            super(context,R.layout.weather_list, temperature);
            this.context = context;
            this.temperature = temperature;
            this.description =description;
            this.image =image;
            this.weekday= weekday;

        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder") View itemView = inflater.inflate(R.layout.weather_list,parent,false);

            TextView Temperature =itemView.findViewById(R.id.txt_temp);
            TextView Description =itemView.findViewById(R.id.txt_weather);
            TextView Weekdays =itemView.findViewById(R.id.txt_day);
            ImageView Images =itemView.findViewById(R.id.img_list);

            Temperature.setText(temperature[position]);
            Weekdays.setText(weekday[position]);
            Description.setText(description[position]);
            Picasso.get().load("https://openweathermap.org/img/w/"+image[position]+".png").into(Images);

            return itemView;
        }
    }
}




