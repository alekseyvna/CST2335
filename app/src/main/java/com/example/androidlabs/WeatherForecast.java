package com.example.androidlabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {


    private ProgressBar progressBar;
    private TextView currenttemp;
    private TextView mintemp;
    private TextView maxtemp;
    private TextView uvrating;
    private ImageView imageee;
    private String icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        ForecastQuery fq = new ForecastQuery();
        fq.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");

        imageee = findViewById(R.id.imageee);
        mintemp = findViewById(R.id.mintemp);
        maxtemp = findViewById(R.id.maxtemp);
        uvrating = findViewById(R.id.uvrating);
        currenttemp = findViewById(R.id.currenttemp);
        progressBar= findViewById(R.id.progbar);
        progressBar.setVisibility(View.VISIBLE);
    }


private class ForecastQuery extends AsyncTask<String, Integer, String> {

        String windspeed;
        String min;
        String max;
        String currtemp;
        Bitmap b;
        String uvratingg;


    @Override
        protected String doInBackground(String...params){

        try {


            HttpURLConnection urlConnection = (HttpURLConnection) new URL(params[0]).openConnection();
            InputStream inStream = urlConnection.getInputStream();


            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inStream, "UTF-8");

            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {

                if (parser.getEventType() == XmlPullParser.START_TAG) {
                    String name = parser.getName();

                    if (name.equals("temperature")) {
                        currtemp = parser.getAttributeValue(null, "value");
                        max = parser.getAttributeValue(null, "max");
                        min = parser.getAttributeValue(null, "min");
                       publishProgress(25);

                    } else if (name.equals("speed")) {
                        windspeed = parser.getAttributeValue(null, "value");
                        publishProgress(50);
                    } else if (name.equals("weather")) {
                        icon = parser.getAttributeValue(null, "icon");
                        publishProgress(75);
                    }

                }
                parser.next();
            }

            if (fileExistance(icon + ".png")) {
                FileInputStream fis = null;


                try {
                    fis = openFileInput(icon + ".png");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                b = BitmapFactory.decodeStream(fis);
            } else {

                Bitmap b = null;
                URL url = new URL("http://openweathermap.org/img/w/" + icon + ".png");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == 200) {
                    b = BitmapFactory.decodeStream(urlConnection.getInputStream());


                    publishProgress(100);


                    FileOutputStream outputStream = openFileOutput(icon + ".png", Context.MODE_PRIVATE);
                    b.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                }
            }

//            urlConnection.getInputStream().close();


            URL url0 = new URL("http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");
            HttpURLConnection UVConnection = (HttpURLConnection) url0.openConnection();
            inStream = UVConnection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            String result = sb.toString();

            //now a JSON table:
            JSONObject jObject = new JSONObject(result);
            double doublee = jObject.getDouble("value");



                uvratingg = Double.toString(doublee);
                Log.i("WEEEEEEE GOT ITTTTTTT", "doInBackground: "+doublee);

                publishProgress(100);
            //END of UV rating

            Thread.sleep(2000); //pause for 2000 milliseconds to watch the progress bar spin



        }catch (Exception e)
    {
        Log.e("Crash!!", e.getMessage() );
    }

            return "Finished task";
}

    public boolean fileExistance(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);


        Log.i("ForecastQuery", "update:" + values[0]);


        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(values[0]);

    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        imageee.setImageBitmap(b);
        currenttemp.setText("Current Temperature: " + currtemp + " C");
        maxtemp.setText("Maximum Temperature: " + max + " C");
        mintemp.setText("Minimum Temperature: " + min + " C");
        uvrating.setText("UV Rating: +" + uvratingg );




        progressBar.setVisibility(View.INVISIBLE);


}

    }
}





