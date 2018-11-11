package oslomet.no.s309854_mappe3.service;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Service extends AsyncTask<String, Void,String> {
    JSONObject jsonObject;
    @Override
    protected String doInBackground(String... urls) { String retur = "";
        String s = "";
        int numberOfElements =0;
        ArrayList<String> list = new ArrayList<>();
        String output = "";
        for (String url : urls) { try {
            URL theUrl = new URL(urls[0]);
            HttpURLConnection conn = (HttpURLConnection)
                    theUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "+ conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader( (conn.getInputStream())));
            System.out.println("Output from Server .... \n"); while ((s = br.readLine()) != null) {
                output = output + s;
            }
            conn.disconnect(); try {
                JSONArray json = new JSONArray(output);
                for (int i = 0; i < json.length(); i++) {
                    JSONObject jsonobject = json.getJSONObject(i);
                    String name = jsonobject.getString("navn");
                    String building = jsonobject.getString("bygning");
                    String room = jsonobject.getString("rom");
                    String date = jsonobject.getString("dato");
                    String time = jsonobject.getString("tidspunkt");

                    retur = name + "--" + building + "--" +room + "--"+ date + "--" + time + "---"  ;
                    list.add(numberOfElements, retur);
                    numberOfElements++;

                }
                return list.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return retur;
        } catch (Exception e) {
            return "Noe gikk feil"; }
        }
        return list.toString();
    }

    @Override
    protected void onPostExecute(String ss) {

    }
}