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

public class Weekly extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly);
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

        new weeklyWeather().execute();
    }

    public class weeklyWeather extends AsyncTask<Void, Void, String>
    {
        private String strUrl = "https://api.darksky.net/forecast/650e29177bd5e8876f1e8b415ddaf77b/30.2672, -97.7431";
        TextView display = (TextView)findViewById(R.id.weeklyWeather);

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

                //Get all Temp High and Lows
                String day1High = data.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getString("temperatureHigh");
                String day1Low = data.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getString("temperatureLow");

                String day2High = data.getJSONObject("daily").getJSONArray("data").getJSONObject(1).getString("temperatureHigh");
                String day2Low = data.getJSONObject("daily").getJSONArray("data").getJSONObject(1).getString("temperatureLow");

                String day3High = data.getJSONObject("daily").getJSONArray("data").getJSONObject(2).getString("temperatureHigh");
                String day3Low = data.getJSONObject("daily").getJSONArray("data").getJSONObject(2).getString("temperatureLow");

                String day4High = data.getJSONObject("daily").getJSONArray("data").getJSONObject(3).getString("temperatureHigh");
                String day4Low = data.getJSONObject("daily").getJSONArray("data").getJSONObject(3).getString("temperatureLow");

                String day5High = data.getJSONObject("daily").getJSONArray("data").getJSONObject(4).getString("temperatureHigh");
                String day5Low = data.getJSONObject("daily").getJSONArray("data").getJSONObject(4).getString("temperatureLow");

                String day6High = data.getJSONObject("daily").getJSONArray("data").getJSONObject(5).getString("temperatureHigh");
                String day6Low = data.getJSONObject("daily").getJSONArray("data").getJSONObject(5).getString("temperatureLow");

                String day7High = data.getJSONObject("daily").getJSONArray("data").getJSONObject(6).getString("temperatureHigh");
                String day7Low = data.getJSONObject("daily").getJSONArray("data").getJSONObject(6).getString("temperatureLow");


                display.setText(
                        "Day 1 High: " + day1High + "\n"
                        + "Day 1 Low: " +  day1Low + "\n"
                        + "Day 2 High: " + day2High + "\n"
                        + "Day 2 Low: " + day2Low + "\n"
                        + "Day 3 High: " + day3High + "\n"
                        + "Day 3 Low: " + day3Low + "\n"
                        + "Day 4 High: " + day4High + "\n"
                        + "Day 4 Low: " + day4Low + "\n"
                        + "Day 5 High: " + day5High + "\n"
                        + "Day 5 Low: " + day5Low + "\n"
                        + "Day 6 High: " + day6High + "\n"
                        + "Day 6 Low: " + day6Low + "\n"
                        + "Day 7 High: " + day7High + "\n"
                        + "Day 7 Low: " + day7Low);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
    }

}
