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
import java.util.List;

import oslomet.no.s309854_mappe3.model.Reservation;
import oslomet.no.s309854_mappe3.model.Room;

public class Service extends AsyncTask<String, Void,String> {
    JSONObject jsonObject;
    List<Room> rooms = new ArrayList<>();
    List<Reservation> reservations = new ArrayList<>();
    @Override
    protected String doInBackground(String... urls) {
        String retur = "";
        String s = "";
        ArrayList<String> list = new ArrayList<>();
        String output = "";

        for(int i= 0; i<urls.length; i++){
            try {
            URL theUrl = new URL(urls[i]);
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
                if(!output.isEmpty()){

                    JSONArray json = new JSONArray(output);

                        if(urls[i].equals("http://student.cs.hioa.no/~s309854/jsonout.php")) {
                            for (int j = 0; j < json.length(); j++) {
                                JSONObject jsonobject = json.getJSONObject(j);
                                String firstName = jsonobject.getString("firstname");
                                String lastName = jsonobject.getString("lastname");
                                String room = jsonobject.getString("room");
                                String date = jsonobject.getString("date");
                                String time = jsonobject.getString("time");
                                reservations.add(new Reservation(firstName, lastName, room, date, time));
                            }
                        }
                        else if(urls[i].equals("http://student.cs.hioa.no/~s309854/rom.php")){
                                for (int j = 0; j < json.length(); j++) {
                                    JSONObject jsonobject = json.getJSONObject(j);
                            String name = jsonobject.getString("name");
                            String details = jsonobject.getString("details");
                            String latitude = jsonobject.getString("latitude");
                            String longitude = jsonobject.getString("longitude");
                            rooms.add(new Room(name , details, latitude, longitude));
                            List<Room> testRoom = rooms;
                        }

                    }
                    output = "";
                } else {
                    return retur;
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            return "Noe gikk feil";
            }
        }
        return retur;
    }

    @Override
    protected void onPostExecute(String ss) {

    }

    public List<Room> getRooms(){
        return this.rooms;
    }

    public List<Reservation> getReservations(){
        return this.reservations;
    }
}