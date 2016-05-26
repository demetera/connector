package com.obir.connector;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.os.AsyncTask;

import java.net.URL;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;


public class ConnectorTest extends Activity implements OnClickListener {
    TextView txt;
    Button   btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (TextView)findViewById(R.id.txt);
        btn = (Button)findViewById(R.id.btn);

        btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                new getData().execute("");
                break;
        }
    }
  
  private class getData extends AsyncTask<String, Void, String> {
    
    String request = "";
    String inputLine;

        @Override
        protected String doInBackground(String... params)
        {
           try              
              {             
                String httpsURL = "https://api.forecast.io/forecast/3c70d6ecf5e1fdb93b6fbaed616daec6/49,19?units=si&exclude=minutely,hourly,alerts,flags,daily";
                URL myurl = new URL(httpsURL);
                HttpsURLConnection con = (HttpsURLConnection)myurl.openConnection();
                InputStream ins = con.getInputStream();
                InputStreamReader isr = new InputStreamReader(ins);
                BufferedReader in = new BufferedReader(isr);

             
                while ((inputLine = in.readLine()) != null)
                      {
                       request = request + inputLine;
                       }
                in.close();              
                } 
          
          catch (Exception e) 
          {
              Thread.interrupted();
          }
                      
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
          
            txt.setText(request);

        }

        @Override
        protected void onPreExecute() {
          
          txt.setText("Updating...");
        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
  
}



//http://stackoverflow.com/questions/9671546/asynctask-android-example
//http://alvinalexander.com/blog/post/java/simple-https-example
//https://developer.android.com/reference/android/os/AsyncTask.html