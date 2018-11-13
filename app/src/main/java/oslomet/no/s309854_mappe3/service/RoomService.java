package oslomet.no.s309854_mappe3.service;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RoomService extends AsyncTask<String, Void,String> {
    JSONObject jsonObject;
    String res = "";
    String rm= "";
    @Override
    protected String doInBackground(String... urls) {
        String retur = "";
        String s = "";
        int numberOfElements =0;
        ArrayList<String> list = new ArrayList<>();
        String output = "";

            try {
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
                conn.disconnect();
                try {
                    JSONArray json = new JSONArray(output);
                        list.add(getRoomnInfo(json).toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } catch (Exception e) {
                return "Noe gikk feil"; }

        String result = list.toString();
        return result;
    }

    @Override
    protected void onPostExecute(String ss) {
        Log.i("postRoom:", ss.toString());


    }


    public List<String> getRoomnInfo(JSONArray json) throws JSONException {
        ArrayList<String> result = new ArrayList<>();
        int numberOfElements =0;
        for (int i = 0; i < json.length(); i++) {
            JSONObject jsonobject = json.getJSONObject(i);
            String name = jsonobject.getString("name");
            String detail = jsonobject.getString("details");
            String latitude = jsonobject.getString("latitude");
            String longitude = jsonobject.getString("longitude");
            String retur = name + "," + detail + "," +latitude + ","+ longitude + "---"  ;
            result.add(numberOfElements, retur);
            numberOfElements++;
        }
        return result;
    }
}