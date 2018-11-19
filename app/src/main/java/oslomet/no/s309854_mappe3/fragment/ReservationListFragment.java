package oslomet.no.s309854_mappe3.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import oslomet.no.s309854_mappe3.MapActivity;
import oslomet.no.s309854_mappe3.R;
import oslomet.no.s309854_mappe3.adapter.ReservationListAdapter;
import oslomet.no.s309854_mappe3.model.Reservation;


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
        List<Reservation> reservations = mapActivity.getReservations();
        adapter = new ReservationListAdapter(getContext(), R.layout.reservasjon_liste_view, reservations);
        listView.setAdapter(adapter);
        return view;
    }

}
