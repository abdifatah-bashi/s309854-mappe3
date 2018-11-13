package oslomet.no.s309854_mappe3.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import oslomet.no.s309854_mappe3.MapActivity;
import oslomet.no.s309854_mappe3.R;
import oslomet.no.s309854_mappe3.adapter.ReservationListAdapter;
import oslomet.no.s309854_mappe3.model.Reservation;
import oslomet.no.s309854_mappe3.service.ReservationListService;


public class ReservationListFragment extends Fragment {
    private ListView listView;
    private ReservationListAdapter adapter;
    private MapActivity mapActivity;

    public ReservationListFragment() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mapActivity = new MapActivity();
        View view = inflater.inflate(R.layout.fragment_reservasjon_liste, container, false);
        listView = view.findViewById(R.id.listview);
        Log.i("testName1: " , "just before");
        ArrayList<Reservation> reservations = null;
        ReservationListService reservationListService = new ReservationListService();

        try {
            String []reservationArray = reservationListService.execute("http://student.cs.hioa.no/~s309854/jsonout.php").get()
                                        .split("---,");
            reservations = mapActivity.getReservations(reservationArray);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {

        }


            for(Reservation r: reservations) Log.i("testName:", r.getFirstName());
        adapter = new ReservationListAdapter(getContext(), R.layout.reservasjon_liste_view, reservations);
        listView.setAdapter(adapter);
        return view;
    }

}
