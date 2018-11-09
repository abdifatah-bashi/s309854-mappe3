package oslomet.no.s309854_mappe3;

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
        int antall =0;
        int antallElement =0;
        ArrayList<String> liste = new ArrayList<>();
        String output = "";
        for (String url : urls) { try {
            URL urlen = new URL(urls[0]);
            HttpURLConnection conn = (HttpURLConnection)
                    urlen.openConnection();
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
                JSONArray mat = new JSONArray(output);
                for (int i = 0; i < mat.length(); i++) {
                    JSONObject jsonobject = mat.getJSONObject(i);
                    String navn = jsonobject.getString("navn");
                    String bygning = jsonobject.getString("bygning");
                    String romNavn = jsonobject.getString("rom");
                    String dato = jsonobject.getString("dato");
                    String tidspunkt = jsonobject.getString("tidspunkt");


                    retur = navn + "--" + bygning + "--" +romNavn + "--"+ dato + "--" + tidspunkt + "---"  ;
                    liste.add(antallElement, retur);
                    antallElement++;


                }
                return liste.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return retur;
        } catch (Exception e) {
            return "Noe gikk feil"; }
        }
        return liste.toString();
    }

    @Override
    protected void onPostExecute(String ss) {

    }
}