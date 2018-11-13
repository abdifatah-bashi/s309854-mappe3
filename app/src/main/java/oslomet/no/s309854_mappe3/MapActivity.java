package oslomet.no.s309854_mappe3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import oslomet.no.s309854_mappe3.adapter.MapMarkerViewAdapter;
import oslomet.no.s309854_mappe3.fragment.NewReservationFragment;
import oslomet.no.s309854_mappe3.fragment.ReservationListFragment;
import oslomet.no.s309854_mappe3.model.Reservation;
import oslomet.no.s309854_mappe3.model.Room;
import oslomet.no.s309854_mappe3.service.RoomService;
import oslomet.no.s309854_mappe3.service.ReservationListService;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private ReservationListFragment reservationListFragment;
    private NewReservationFragment newReservationFragment;
    private BottomNavigationView mainNav;
    private GoogleMap mMap;

    // Coordinates for OsloMet rooms
    LatLng PH320 = null;
    LatLng PH350 = null;
    LatLng PH460 = null;
    LatLng PI480 = null;
    LatLng PI520 = null;

    String PH320Reservation = "Ingen reservasjon i dag!";
    String PH350Reservation = "Ingen reservasjon i dag!";
    String PH460Reservation = "Ingen reservasjon i dag!";
    String PI480Reservation = "Ingen reservasjon i dag!";
    String PI520Reservation = "Ingen reservasjon i dag!";

    //Services
    ReservationListService reservationListService = new ReservationListService();
    RoomService roomService = new RoomService();

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
                switch (menuItem.getItemId()) {
                    case R.id.home:
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
                        return true;

                    default:
                        return false;
                }

            }
        });


    }

    public void visFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        if (fragment.isHidden()) {
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commit();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String reservationResult = "";
        String roomResult = "";
        try {
            reservationResult = reservationListService.execute("http://student.cs.hioa.no/~s309854/jsonout.php").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            roomResult = roomService.execute("http://student.cs.hioa.no/~s309854/rom.php").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        String reservationArray[] = reservationResult.toString().split("---,");
        String roomArray[] = roomResult.toString().split("---,");
        getReservations(reservationArray);

        setRoomMarkers(getRooms(roomArray));
        setRoomReservations(getReservations(reservationArray));

        mMap.addMarker(new MarkerOptions().position(PH320).title("P32")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .snippet(PH320Reservation)
        );
        mMap.addMarker(new MarkerOptions().position(PH350).title("P35")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .snippet(PH350Reservation));
        mMap.addMarker(new MarkerOptions().position(PH460).title("P46")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .snippet(PH460Reservation));
        mMap.addMarker(new MarkerOptions().position(PI480).title("P48")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .snippet(PI480Reservation));
        mMap.addMarker(new MarkerOptions().position(PI520).title("P52")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .snippet(PI520Reservation));

        mMap.setInfoWindowAdapter(new MapMarkerViewAdapter(getLayoutInflater()));
        float zoomLevel = 16.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PH320, zoomLevel));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PH350, zoomLevel));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PH460, zoomLevel));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PI480, zoomLevel));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PI520, zoomLevel));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PH320, zoomLevel));
    }


    public ArrayList<Reservation> getReservations(String[] reservationArray) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        for (String reservation : reservationArray) {
            String firstname = reservation.split(",")[0].replace("[", "");
            String lastname = reservation.split(",")[1];
            String room = reservation.split(",")[2];
            String date = reservation.split(",")[3];
            String time = reservation.split(",")[4];
            String from = time.split("-")[0];
            String to = time.split("-")[1];
            reservations.add(new Reservation(firstname, lastname, room, date, from, to));

        }

        return reservations;

    }


    public ArrayList<Room> getRooms(String[] roomArray) {

        ArrayList<Room> rooms = new ArrayList<>();

        for (String room : roomArray) {
            String name = room.split(",")[0].replace("[", "");
            String details = room.split(",")[1];
            String latitude = room.split(",")[2];
            String longtitude = room.split(",")[3].replace("---]", "");
            rooms.add(new Room(name, details, latitude, longtitude));
        }
        return rooms;
    }


    public String getCurrentDate() {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

    }

    public void setRoomMarkers(ArrayList<Room > rooms){
        for (Room r : rooms) {
            String roomName = r.getName().trim();
            if (roomName.equals("PH320")) {
                PH320 = new LatLng(Double.parseDouble(r.getLatitude()), Double.parseDouble(r.getLongitude()));
            } else if (roomName.equals("PH350")) {
                PH350 = new LatLng(Double.parseDouble(r.getLatitude()), Double.parseDouble(r.getLongitude()));
            } else if (roomName.equals("PH460")) {
                PH460 = new LatLng(Double.parseDouble(r.getLatitude()), Double.parseDouble(r.getLongitude()));
            } else if (roomName.equals("PI480")) {
                PI480 = new LatLng(Double.parseDouble(r.getLatitude()), Double.parseDouble(r.getLongitude()));
            } else if (roomName.equals("PI520")) {
                PI520 = new LatLng(Double.parseDouble(r.getLatitude()), Double.parseDouble(r.getLongitude().replace("]", "")));
            }

        }

    }

    public void setRoomReservations(ArrayList<Reservation> reservations){
        for (Reservation r : reservations) {
            String reservasjonInfo = "Fornavn: " + r.getFirstName() + "\n" +
                    "Etternavn: " + r.getLastName() + "\n" +
                    "Rom: " + r.getRoom() + "\n" +
                    "Dato " + r.getDate() + "\n" +
                    "Fra: " + r.getFrom() + "\n" +
                    "Til: " + r.getTo() + "\n";
            switch (r.getRoom()) {
                case "PH320":
                    if (getCurrentDate().equals(r.getDate())) {
                        PH320Reservation = reservasjonInfo;
                    }
                case "PH350":
                    if (getCurrentDate().equals(r.getDate())) {
                        PH350Reservation = reservasjonInfo;
                    }
                case "PH460":
                    if (getCurrentDate().equals(r.getDate())) {
                        PH460Reservation = reservasjonInfo;
                    }
                case "PI480":
                    if (getCurrentDate().equals(r.getDate())) {
                        PI480Reservation = reservasjonInfo;
                    }
                case "PI520":
                    if (getCurrentDate().equals(r.getDate())) {
                        PI520Reservation = reservasjonInfo;
                    }

            }
        }


    }


}
