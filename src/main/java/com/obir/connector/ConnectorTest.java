package com.obir.connector;
 
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
 
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
 
import javax.net.ssl.HttpsURLConnection;
 
public class ConnectorTest extends Activity implements OnClickListener
{
    Button btn1;
    Button btn2;
    TextView txt1;
    TextView txt2;
 
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        txt1 = (TextView)findViewById(R.id.txt1);
        txt2 = (TextView)findViewById(R.id.txt2);
 
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
 
    }
 
    @Override
    public void onClick (View v)
    {
        switch (v.getId())
        {
            case R.id.btn1:
                new getData().execute("");
                break;
            case R.id.btn2:
                txt2.setText("");
                txt1.setText("Press Get Data");
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
 
            txt2.setText(request);
            txt1.setText("Updated");
 
        }
 
        @Override
        protected void onPreExecute() {
 
            txt1.setText("Updating...");
        }
 
        @Override
        protected void onProgressUpdate(Void... values) {}
    }
  
}