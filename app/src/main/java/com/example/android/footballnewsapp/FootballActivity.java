package com.example.android.footballnewsapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FootballActivity extends AppCompatActivity {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = FootballActivity.class.getSimpleName();

    /**
     * URL to query the GUARDIAN dataset for football news
     */
    private static final String FOOTBALL_REQUEST_URL =
            "https://content.guardianapis.com/football?api-key=14153493-2fcc-42d8-9cc6-5a202222e671";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.football_list_item);

        // Kick off an {@link AsyncTask} to perform the network request
        FootballAsyncTask task = new FootballAsyncTask();
        task.execute();
    }

    /**
     * Update the screen to display information from the given {@link Football}.
     */
    private void updateUi(Football football) {
        // Display the Type of News in the UI
        TextView typeTextView = findViewById(R.id.type_tv);
        typeTextView.setText(football.type);

        // Display the Section in the UI
        TextView sectionTextView = findViewById(R.id.section_tv);
        sectionTextView.setText(football.section);

        // Display the Title in the UI
        TextView titleTextView = findViewById(R.id.title_tv);
        titleTextView.setText(football.title);

        // Display the Date in the UI
        TextView dateTextView = findViewById(R.id.date_tv);
        dateTextView.setText(football.date);
    }

//    /**
//     * Returns a formatted date and time string for when the earthquake happened.
//     */
//    private String getDateString(long timeInMilliseconds) {
//        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy 'at' HH:mm:ss z");
//        return formatter.format(timeInMilliseconds);
//    }

//    /**
//     * Return the display string for whether or not there was a tsunami alert for an earthquake.
//     */
//    private String getTsunamiAlertString(int tsunamiAlert) {
//        switch (tsunamiAlert) {
//            case 0:
//                return getString(R.string.alert_no);
//            case 1:
//                return getString(R.string.alert_yes);
//            default:
//                return getString(R.string.alert_not_available);
//        }
//    }

    /**
     * {@link AsyncTask} to perform the network request on a background thread, and then
     * update the UI with the first earthquake in the response.
     */
    private class FootballAsyncTask extends AsyncTask<URL, Void, Football> {

        @Override
        protected Football doInBackground(URL... urls) {

            // Create URL object
            URL url = createUrl(FOOTBALL_REQUEST_URL);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);

            } catch (IOException e) {
                // TODO Handle the IOException
                Log.e(LOG_TAG, "Problem making the HTTP request.", e);
            }

            // Extract relevant fields from the JSON response and create an {@link Event} object
            Football football = extractFeatureFromJson(jsonResponse);

            // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
            return football;
        }

        /**
         * Update the screen with the given earthquake (which was the result of the
         * {@link FootballAsyncTask}).
         */
        @Override
        protected void onPostExecute(Football football) {
            if (football == null) {
                return;
            }

            updateUi(football);
        }

        /**
         * Returns new URL object from the given string URL.
         */
        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e(LOG_TAG, "Error with creating URL", exception);
                return null;
            }
            return url;
        }

        /**
         * Make an HTTP request to the given URL and return a String as the response.
         */
        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";

            // If the URL is null, then return early.
            if (url == null) {
                return jsonResponse;
            }

            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();

                // If the request was successful (response code 200),
                // then read the input stream and parse the response.
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                } else {
                    Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());

                }

            } catch (IOException e) {
                // TODO: Handle the exception
                Log.e(LOG_TAG, "Problem retrieving the football JSON results.", e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        /**
         * Convert the {@link InputStream} into a String which contains the
         * whole JSON response from the server.
         */
        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        /**
         * Return an {@link Football} object by parsing out information
         * about the first earthquake from the input earthquakeJSON string.
         */
        private Football extractFeatureFromJson(String footballJSON) {
            //If the JSON string is empty or null, then return early.
            if (TextUtils.isEmpty(footballJSON)) {
                return null;
            }
            try {
                JSONObject baseJsonResponse = new JSONObject(footballJSON);
                JSONArray featureArray = baseJsonResponse.getJSONArray("features");

                // If there are results in the features array
                if (featureArray.length() > 0) {
                    // Extract out the first feature (which is a piece of news)
                    JSONObject firstFeature = featureArray.getJSONObject(0);
                    JSONObject properties = firstFeature.getJSONObject("properties");

                    // Extract out the title, time, and tsunami values
                    String typeNews = properties.getString("type");
                    String sectionName = properties.getString("sectionName");
                    String webTitle = properties.getString("webTitle");
                    String newsDate = properties.getString("webPublicationDate");

                    // Create a new {@link Event} object
                    return new Football(typeNews, sectionName, webTitle, newsDate);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
            }
            return null;
        }
    }
}
