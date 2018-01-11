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

public class Hourly extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly);
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

        new hourlyWeather().execute();
    }


    public class hourlyWeather extends AsyncTask<Void, Void, String>
    {
        private String strUrl = "https://api.darksky.net/forecast/650e29177bd5e8876f1e8b415ddaf77b/30.2672, -97.7431";
        TextView display1 = (TextView)findViewById(R.id.hourlyWeather);
        TextView display2 = (TextView)findViewById(R.id.hourlyWeather2);
        TextView display3 = (TextView)findViewById(R.id.hourlyWeather3);
        TextView display4 = (TextView)findViewById(R.id.hourlyWeather4);
        TextView display5 = (TextView)findViewById(R.id.hourlyWeather5);

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

            try
            {
                JSONObject data = new JSONObject(s);
                String hour1 = "In one Hour: " + data.getJSONObject("hourly").getJSONArray("data").getJSONObject(0).getString("temperature");
                String hour2 = "In two hours: " + data.getJSONObject("hourly").getJSONArray("data").getJSONObject(1).getString("temperature");
                String hour3 = "In three hours: " + data.getJSONObject("hourly").getJSONArray("data").getJSONObject(2).getString("temperature");
                String hour4 = "In four hours: " + data.getJSONObject("hourly").getJSONArray("data").getJSONObject(3).getString("temperature");
                String hour5 = "In five hours: " + data.getJSONObject("hourly").getJSONArray("data").getJSONObject(4).getString("temperature");

                display1.setText(hour1 + "\n");
                display2.setText(hour2 + "\n");
                display3.setText(hour3 + "\n");
                display4.setText(hour4 + "\n");
                display5.setText(hour5 + "\n");

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
    }

}
