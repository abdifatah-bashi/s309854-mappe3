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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import oslomet.no.s309854_mappe3.adapter.MapMarkerViewAdapter;
import oslomet.no.s309854_mappe3.fragment.AddFragment;
import oslomet.no.s309854_mappe3.fragment.NewReservationFragment;
import oslomet.no.s309854_mappe3.fragment.ReservationListFragment;
import oslomet.no.s309854_mappe3.model.Reservation;
import oslomet.no.s309854_mappe3.model.Room;
import oslomet.no.s309854_mappe3.service.Service;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private ReservationListFragment reservationListFragment;
    private NewReservationFragment newReservationFragment;
    private AddFragment addFragment;
    private BottomNavigationView mainNav;
    private GoogleMap mMap;
    Service service = new Service();
    List<Room> rooms;
    List<Reservation> reservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aktivitet_kart);
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        reservations = new ArrayList<>();
        rooms = new ArrayList<>();

        reservationListFragment = new ReservationListFragment();
        newReservationFragment = new NewReservationFragment();
        addFragment = new AddFragment();
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
                        showFragment(addFragment);
                        mapFragment.getView().setVisibility(View.INVISIBLE);
                        return true;
                    case R.id.liste:
                        showFragment(reservationListFragment);
                        mapFragment.getView().setVisibility(View.INVISIBLE);
                        return  true;

                    default: return  false;
                }

            }
        });



    }

    public void showFragment(Fragment fragment){
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
        setMarkers(getRooms());


    }

    public void init(){
        try {
            service.execute(new
                    String[]{"http://student.cs.hioa.no/~s309854/jsonout.php", "http://student.cs.hioa.no/~s309854/rom.php"}).get();
            this.reservations = service.getReservations();
            this.rooms = service.getRooms();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    public List<Room> getRooms(){
        init();

        for(Room r: rooms ){
            Log.i("rooms: " , r.getName() + " " + r.getDetails() );
        }
        return this.rooms;

    }

    public List<Reservation> getReservations(){
        init();
        return this.reservations;
    }

    public String getCurrentDate(){
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

    }

    public void setMarkers(List<Room> rooms){
        for(Room r: rooms ){
            Double latitude = Double.parseDouble(r.getLatitude());
            Double longitude = Double.parseDouble(r.getLongitude());
            LatLng latLng = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(latLng).title(r.getName())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                    .snippet(setReservationInfo(r.getName()))
            );
            mMap.setInfoWindowAdapter(new MapMarkerViewAdapter(getLayoutInflater()));
            float zoomLevel = 16.0f;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));

        }
    }


    public String setReservationInfo(String roomName){
        Room room = getRoom(roomName);
        for (Reservation r : reservations) {
            if(r.getRoom().equals(roomName) && r.getDate().equals(getCurrentDate())) {
                String from = r.getTime().split("-")[0];
                String to = r.getTime().split("-")[1];
                room.setReservationInfo(room.getReservationInfo() + "Fra: " + from
                        + "\n Til:  " + to +  "\n -------------- \n");
                }
            }
            return room.getReservationInfo();
        }

  public Room getRoom(String roomName){
        Room room = null;
        for(Room r: rooms) if(r.getName().equals(roomName)) {
            room = r;
        }
        return room;
  }


}
