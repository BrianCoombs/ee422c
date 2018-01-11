package com.example.brianc.weatherapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Past extends AppCompatActivity {

    String ans = "";
    String strUrl = "https://api.darksky.net/forecast/650e29177bd5e8876f1e8b415ddaf77b/30.2672, -97.7431,";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past);
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

        Button getData = (Button) findViewById(R.id.getData);
        final EditText result   = (EditText)findViewById(R.id.Input);


        getData.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        ans = result.getText().toString();
                        strUrl = strUrl + " " + ans + "T00:00:00";
                        new pastWeather().execute();
                    }
                });


    }

    public class pastWeather extends AsyncTask<Void, Void, String>
    {
        TextView display = (TextView)findViewById(R.id.displayResult);

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
                String temp = data.getJSONObject("currently").getString("temperature");
                display.setText("Temperature on that day is: " + temp);

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
    }

}
