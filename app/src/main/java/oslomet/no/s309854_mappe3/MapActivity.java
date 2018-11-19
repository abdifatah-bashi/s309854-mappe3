package oslomet.no.s309854_mappe3;

import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import oslomet.no.s309854_mappe3.adapter.MapMarkerViewAdapter;
import oslomet.no.s309854_mappe3.fragment.NewReservationFragment;
import oslomet.no.s309854_mappe3.fragment.ReservationListFragment;
import oslomet.no.s309854_mappe3.model.Reservation;
import oslomet.no.s309854_mappe3.service.Service;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private ReservationListFragment reservationListFragment;
    private NewReservationFragment newReservationFragment;
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
        reservationListFragment = new ReservationListFragment();
        newReservationFragment = new NewReservationFragment();
        mainNav = findViewById(R.id.main_nav);

        mainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home :
                        Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.reserver:
                        visFragment(newReservationFragment);
                        mapFragment.getView().setVisibility(View.INVISIBLE);
                        return true;
                    case R.id.liste:
                        visFragment(reservationListFragment);
                        mapFragment.getView().setVisibility(View.INVISIBLE);
                        return  true;

                    default: return  false;
                }

            }
        });




    }



    public void visFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        if(fragment.isHidden()){
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commit();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String output= "";
        List<Reservation> reservasjoner = hentReservasjoner();
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
            for(Reservation r: reservasjoner ){
                String reservasjonInfo = "Info";
                switch (r.getRoom() ){
                    case "PH320":
                        if(hentDagensDato().equals(r.getDate())){
                            p32Reservasjon = reservasjonInfo;
                        }
                        break;
                    case "PH350":
                        if(hentDagensDato().equals(r.getDate())){
                            p35Reservasjon = reservasjonInfo;
                        }
                        break;
                    case "PH460":
                        if(hentDagensDato().equals(r.getDate())){
                            p46Reservasjon = reservasjonInfo;
                        }
                        break;
                    case "PI480":
                        if(hentDagensDato().equals(r.getDate())){
                            p48Reservasjon = reservasjonInfo;
                        }
                        break;
                    case "PI520":
                        if(hentDagensDato().equals(r.getDate())){
                            p52Reservasjon = reservasjonInfo;
                        }
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

        mMap.setInfoWindowAdapter(new MapMarkerViewAdapter(getLayoutInflater()));
        float zoomLevel = 16.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(P32, zoomLevel));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(P35, zoomLevel));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(P46, zoomLevel));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(P48, zoomLevel));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(P52, zoomLevel));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(P32, zoomLevel));
    }

    public List<Reservation> hentReservasjoner(){
        String result = null;
        List<Reservation> reservations = new ArrayList<>();
        try {
            result = service.execute(new
                    String[]{"http://student.cs.hioa.no/~s309854/jsonout.php"}).get();
            reservations = service.getReservations();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return reservations;

    }

    public String hentDagensDato(){
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

    }
}
