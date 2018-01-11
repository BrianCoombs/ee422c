package com.example.brianc.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity
{
    //String url = "https://api.darksky.net/forecast/650e29177bd5e8876f1e8b415ddaf77b/37.8267,-122.4233";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button curWeather = (Button) findViewById(R.id.curWeather);
        Button Hourly = (Button) findViewById(R.id.Hourly);
        Button TwoDays = (Button) findViewById(R.id.TwoDays);
        Button Week = (Button) findViewById(R.id.Week);
        Button Past = (Button) findViewById(R.id.Past);


        curWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CurrentWeather.class));

            }
        });

        Hourly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Hourly.class));

            }
        });

        TwoDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Avg48Hours.class));

            }
        });

        Week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Weekly.class));

            }
        });

        Past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Past.class));

            }
        });



    }
}


