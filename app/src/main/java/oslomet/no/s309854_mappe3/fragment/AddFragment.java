package oslomet.no.s309854_mappe3.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import oslomet.no.s309854_mappe3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment {

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Fragments
        final NewReservationFragment newReservationFragment = new NewReservationFragment();
        final NewRoomFragment newRoomFragment = new NewRoomFragment();


        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_add, container, false);
        Button addReservasjonBtn = view.findViewById(R.id.add_reserv);
        Button addRoom = view.findViewById(R.id.add_room);

        addReservasjonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(newReservationFragment);

            }
        });

        addRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(newRoomFragment);
            }
        });
        return view;
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        if (fragment.isHidden()) {
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commit();
    }


}
