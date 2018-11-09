package oslomet.no.s309854_mappe3;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReservasjonListeFragment extends Fragment {
 private ListView listView;
 private ReservasjonListeAdapter adapter;
 private KartAktivitet kartAktivitet;

    public ReservasjonListeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        kartAktivitet = new KartAktivitet();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reservasjon_liste, container, false);

        listView = view.findViewById(R.id.listview);
        ArrayList<Reservasjon> reservasjoner = kartAktivitet.hentReservasjoner();

         adapter = new ReservasjonListeAdapter( getContext(), R.layout.reservasjon_liste_view, reservasjoner);
        listView.setAdapter(adapter);







        return view;
    }

}
