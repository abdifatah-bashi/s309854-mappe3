package oslomet.no.s309854_mappe3;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class KartAktivitet extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public String reservasjon = "";
    public int antallElement = 0;
    getJSON task = new getJSON();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aktivitet_kart);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String output= "";
        ArrayList<Reservasjon> reservasjoner = new ArrayList<>();
        String p32Reservasjon = "Ingen reservasjon i dag!";
        String p35Reservasjon = "Ingen reservasjon i dag!";
        String p46Reservasjon = "Ingen reservasjon i dag!";
        String p48Reservasjon = "Ingen reservasjon i dag!";
        String p52Reservasjon = "Ingen reservasjon i dag!";

        // Add a marker in Sydney and move the camera
        LatLng P35 = new LatLng(59.9196143, 10.7350223);
        LatLng P46 = new LatLng(   59.9212465, 10.733519600000022);
        LatLng P32 = new LatLng(   59.9200718, 10.735783800000036);
        LatLng P48 = new LatLng(   59.9214455, 10.732669999999985);
        LatLng P52 = new LatLng(   59.9223554, 10.732217699999978);


        try {
            String result = task.execute(new
                    String[]{"http://student.cs.hioa.no/~s309854/jsonout.php"}).get();
            Log.i("out: " , result);
            Log.i("testAntall", "antall elementer: " + antallElement);
            for (int i = 0; i <antallElement ; i++) {

                String navn = result.split("---")[i].split("--")[0].replace("[" , "");
                String bygning = result.split("---")[i].split("--")[1];
                String rom = result.split("---")[i].split("--")[2];
                String dato = result.split("---")[i].split("--")[3];
                String fra = result.split("---")[i].split("--")[4];
                String til = result.split("---")[i].split("--")[5];
                Log.i("testN", navn + " , " + bygning);
                reservasjoner.add(i, new Reservasjon(navn, bygning, rom, dato, fra, til));

            }
            Log.i("testR: " , Arrays.toString(reservasjoner.toArray()));
            for(Reservasjon r: reservasjoner ){
                String reservasjonInfo = "Navn: " + r.getNavn() + "\n" +
                        "Rom: " + r.getRom() + "\n" +
                        "Dato " + r.getDato() + "\n" +
                        "Fra: " + r.getFra()+ "\n" +
                        "Til: " + r.getTil() + "\n" ;



                switch (r.getBygning() ){
                    case "P32": p32Reservasjon = reservasjonInfo;
                        break;
                    case "P35": p35Reservasjon = reservasjonInfo;
                        break;
                    case "P46": p46Reservasjon = reservasjonInfo;
                        break;
                    case "P48": p48Reservasjon = reservasjonInfo;
                        break;
                    case "PH52": p52Reservasjon = reservasjonInfo;
                        break;

                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        mMap.addMarker(new MarkerOptions().position(P32).title("P32")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .snippet(p32Reservasjon)
        );
        mMap.addMarker(new MarkerOptions().position(P35).title("P35")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .snippet(p35Reservasjon));
        mMap.addMarker(new MarkerOptions().position(P46).title("P46")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .snippet(p46Reservasjon));
        mMap.addMarker(new MarkerOptions().position(P48).title("P48")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .snippet(p48Reservasjon));
        mMap.addMarker(new MarkerOptions().position(P52).title("P52")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .snippet(p52Reservasjon));



        mMap.setInfoWindowAdapter(new BalloonAdapter(getLayoutInflater()));
        float zoomLevel = 16.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(P32, zoomLevel));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(P35, zoomLevel));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(P46, zoomLevel));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(P48, zoomLevel));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(P52, zoomLevel));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(P32, zoomLevel));


    }

    private class getJSON extends AsyncTask<String, Void,String> {
        JSONObject jsonObject;
        @Override
        protected String doInBackground(String... urls) { String retur = "";
            String s = "";
            int antall =0;
            ArrayList<String> liste = new ArrayList<>();
            String output = "";
            for (String url : urls) { try {
                URL urlen = new URL(urls[0]); HttpURLConnection conn = (HttpURLConnection)
                        urlen.openConnection(); conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json"); if (conn.getResponseCode() != 200) {
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
                        String fra = jsonobject.getString("fra_klokkeslett");
                        String til = jsonobject.getString("til_klokkeslett");

                        retur = navn + "--" + bygning + "--" +romNavn + "--"+ dato + "--" + fra + "--" + til + "---"  ;
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


}
