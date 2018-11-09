package oslomet.no.s309854_mappe3;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class KartAktivitet extends FragmentActivity implements OnMapReadyCallback {

    private ReservasjonListeFragment reservasjonListeFragment;
    private ReservasjonFragment reservasjonFragment;
    private BottomNavigationView mainNav;
    private GoogleMap mMap;
    Service service = new Service();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aktivitet_kart);
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        reservasjonListeFragment = new ReservasjonListeFragment();
        reservasjonFragment = new ReservasjonFragment();
        mainNav = findViewById(R.id.main_nav);

        mainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home :
                        mapFragment.getView().setVisibility(View.VISIBLE);
                        hideFragment(reservasjonListeFragment);
                        hideFragment(reservasjonFragment);
                        return true;

                    case R.id.reserver:
                        setFragment(reservasjonFragment);
                        mapFragment.getView().setVisibility(View.INVISIBLE);
                        return true;
                    case R.id.liste:
                        setFragment(reservasjonListeFragment);
                        mapFragment.getView().setVisibility(View.INVISIBLE);
                        return  true;

                    default: return  false;
                }

            }
        });




    }

    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        if(fragment.isHidden()){
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commit();
    }

    public void hideFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String output= "";
        ArrayList<Reservasjon> reservasjoner = hentReservasjoner();
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

    public ArrayList<Reservasjon> hentReservasjoner(){
        String result = null;
        ArrayList<Reservasjon> reservasjoner = new ArrayList<>();
        try {
            result = service.execute(new
                    String[]{"http://student.cs.hioa.no/~s309854/jsonout.php"}).get();
            int antallElement = result.split("---").length-1;
            Log.i("antallElementTest: ", antallElement + "");
            for (int i = 0; i <antallElement ; i++) {

                String navn = result.split("---")[i].split("--")[0].replace("[" , "");
                String bygning = result.split("---")[i].split("--")[1];
                String rom = result.split("---")[i].split("--")[2];
                String dato = result.split("---")[i].split("--")[3];
                String tidspunkt = result.split("---")[i].split("--")[4];
                String fra = tidspunkt.split("-")[0];
                String til = tidspunkt.split("-")[1];
                Log.i("testN", navn + " , " + bygning);
                reservasjoner.add(i, new Reservasjon(navn, bygning, rom, dato, fra, til));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return reservasjoner;

    }
}
