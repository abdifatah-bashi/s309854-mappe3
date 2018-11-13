package oslomet.no.s309854_mappe3.service;

import android.os.AsyncTask;
import java.net.HttpURLConnection;
import java.net.URL;


public class AddReservationService extends AsyncTask<String, Void,String> {
    @Override
    protected String doInBackground(String... urls) {
        String result = "";
        try {
                URL theUrl = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection)
                        theUrl.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "+ conn.getResponseCode());
                }
                conn.disconnect();
            } catch (Exception e) {
                return "Noe gikk feil"; }
        return result;

    }

    @Override
    protected void onPostExecute(String ss) {

    }
}