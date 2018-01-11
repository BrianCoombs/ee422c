package com.example.brianc.weatherapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Avg48Hours extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avg48_hours);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        new avg48HoursWeather().execute();
    }


    public class avg48HoursWeather extends AsyncTask<Void, Void, String>
    {
        private String strUrl = "https://api.darksky.net/forecast/650e29177bd5e8876f1e8b415ddaf77b/30.2672, -97.7431";
        TextView display = (TextView)findViewById(R.id.avg48);

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(Void... voids)
        {
            try
            {
                URL url = new URL(strUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder data = new StringBuilder();
                String temp;

                while((temp = reader.readLine()) != null)
                {
                    data.append(temp).append("\n");
                }

                reader.close();

                String extracted = data.toString();

                return extracted;

            }
            catch (Exception e)
            {
                return null;
            }

        }


        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            //display.setText(s);

            try
            {
                JSONObject data = new JSONObject(s);

                //Get the Values
                String day1High = data.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getString("temperatureHigh");
                String day1Low = data.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getString("temperatureLow");
                String day2High = data.getJSONObject("daily").getJSONArray("data").getJSONObject(1).getString("temperatureHigh");
                String day2Low = data.getJSONObject("daily").getJSONArray("data").getJSONObject(1).getString("temperatureLow");

                double high1 = Double.parseDouble(day1High);
                double low1 = Double.parseDouble(day1Low);
                double high2 = Double.parseDouble(day2High);
                double low2 = Double.parseDouble(day2Low);

                double avg = ((high1 + low1 + high2 + low2) / 4);

                display.setText("48 Hour Average: " + avg);

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
    }


}
