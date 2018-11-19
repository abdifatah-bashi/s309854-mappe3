package oslomet.no.s309854_mappe3.fragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.circulardialog.CDialog;
import com.example.circulardialog.extras.CDConstants;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import oslomet.no.s309854_mappe3.MapActivity;
import oslomet.no.s309854_mappe3.R;
import oslomet.no.s309854_mappe3.model.Reservation;
import oslomet.no.s309854_mappe3.model.Room;
import oslomet.no.s309854_mappe3.service.Service;

public class NewReservationFragment extends Fragment {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String room;
    private String date;
    private String time;
    private RadioButton hour1;
    private RadioButton hour2;
    private RadioButton hour3;
    private RadioButton hour4;
    private Spinner buildingDropdown;
    private Spinner roomDropdown;
    private Button dateBtn;
    private Button reservBtn;
    private EditText firstnameEditView;
    private EditText lastnameEditView;
    private RadioGroup radioGroup;
    Service service;

    public NewReservationFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservasjon, container, false);

        // Init variables
        dateBtn = view.findViewById(R.id.date);
        dateBtn.setTransformationMethod(null);
        hour1 = view.findViewById(R.id.time1);
        hour2 = view.findViewById(R.id.time2);
        hour3 = view.findViewById(R.id.time3);
        hour4 = view.findViewById(R.id.time4);
        firstnameEditView = view.findViewById(R.id.firstname);
        lastnameEditView = view.findViewById(R.id.lastname);

        reservBtn = view.findViewById(R.id.reserver_btn);
        roomDropdown = view.findViewById(R.id.room);
        radioGroup = view.findViewById(R.id.radio);
        service = new Service();

        // Show dropdown
        showDateDropdown(dateBtn);
        showRoomDropdown(roomDropdown);

        reservBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReservation(firstnameEditView.getText().toString(), lastnameEditView.getText().toString(),
                        roomDropdown.getSelectedItem().toString(), showDateDropdown(dateBtn), time);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch (checkedId) {
                    case R.id.time1:
                        time = "08:30-10:15";
                        break;
                    case R.id.time2:
                        time = "10:30-12:15";
                        break;
                    case R.id.time3:
                        time = "12:30-14:15";
                        break;
                    case R.id.time4:
                        time = "14:30-16:15";
                        break;
                }
            }
        });



        return view;
    }


    public String showDateDropdown(final Button dateBtn) {


        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                ;

                String date = day + "/" + month + "/" + year;
                NewReservationFragment.this.date = date;
                dateBtn.setText("Valgt date: " + date);

                room = roomDropdown.getSelectedItem().toString();
                showAvailableHours(room, NewReservationFragment.this.date);

            }
        };

        return date;
    }

    public void addReservation(String firstname, String lastname, String room,
                               String date, String time) {

        ReservationListFragment listeFragment = new ReservationListFragment();
        String url = "http://student.cs.hioa.no/~s309854/jsonin.php?firstname=" + firstname + "&lastname=" + lastname
                + "&room=" + room + "&date=" + date + "&time=" + time;
        if (isFormValid(firstname, lastname , room, date, time)) {
            service.execute(url);
            showConfirmationAlert();
            showAllReservations(listeFragment);
        } else {
            showErrorAlert();
        }


    }


    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        if (fragment.isHidden()) {
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commit();
    }


    public void showAllReservations(final Fragment fragment) {
        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        setFragment(fragment);
                    }
                }, 3000);
    }


    public boolean isFormValid(String firstname, String lastname,  String rom,
                               String dato, String tidspunkt) {
        String nameRegEx = "(?i)(^[a-z]+)[a-z .,-]((?! .,-)$){1,20}$";

        if (firstname == null || firstname.trim().isEmpty() || !firstname.matches(nameRegEx)
                || lastname == null || lastname.trim().isEmpty() || !lastname.matches(nameRegEx)
                || rom.equals("Velg room") || dato == null || tidspunkt == null) {
            return false;
        }
        return true;
    }



    public String showRoomDropdown(Spinner spinner) {
        MapActivity mapActivity = new MapActivity();
        final String[] rom = new String[1];
        String[] romListe = getRoomNames(mapActivity.getRooms());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, romListe);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                rom[0] = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return rom[0];

    }


    public void showAvailableHours(String room, String date) {
        MapActivity mapActivity = new MapActivity();
        List<Reservation> reservations = mapActivity.getReservations();
        for (Reservation r : reservations) {
            String tidspunkt = r.getTime();
            if ( r.getRoom().equals(room) && r.getDate().equals(date)) {
                disableHour(tidspunkt);

            } else {
                ActivateHour(tidspunkt);
            }
        }
    }

    public void disableHour(String time) {
        if (time.equals("08:3010:15")) {
            hour1.setPaintFlags(hour1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            hour1.setEnabled(false);


        } else if (time.equals("10:3012:15")) {
            hour2.setPaintFlags(hour2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            hour2.setEnabled(false);

        } else if (time.equals("12:3014:15")) {
            hour3.setPaintFlags(hour3.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            hour3.setEnabled(false);
        } else {
            hour4.setPaintFlags(hour4.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            hour4.setEnabled(false);

        }

    }

    public void showErrorAlert() {
        new FancyAlertDialog.Builder((Activity) getContext())
                .setTitle("Vennligst fyll alle feltene i skjemaet!")
                .setBackgroundColor(Color.parseColor("#BF360C"))  //Don't pass R.color.colorvalue
                .setNegativeBtnText("Avbryt")
                .setPositiveBtnBackground(Color.parseColor("#1B5E20"))  //Don't pass R.color.colorvalue
                .setPositiveBtnText("OK")
                .setNegativeBtnBackground(Color.parseColor("#BF360C"))  //Don't pass R.color.colorvalue
                .setAnimation(Animation.POP)
                .setIcon(R.drawable.ic_error_outline_black_24dp, Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {

                    }
                })
                .OnNegativeClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {

                    }
                })
                .build();
    }

    public void showConfirmationAlert() {
        new CDialog(getContext()).createAlert("Reservert!",
                CDConstants.SUCCESS,   // Type of dialog
                CDConstants.LARGE)    //  size of dialog
                .setAnimation(CDConstants.SCALE_FROM_BOTTOM_TO_TOP)     //  Animation for enter/exit
                .setDuration(3000)   // in milliseconds
                .setTextSize(CDConstants.LARGE_TEXT_SIZE)  // CDConstants.LARGE_TEXT_SIZE, CDConstants.NORMAL_TEXT_SIZE
                .show();

    }

    public void ActivateHour(String time) {
        if (time.equals("08:3010:15")) {
            hour1.setEnabled(true);
            hour1.setPaintFlags(0);

        } else if (time.equals("10:3012:15")) {
            hour2.setEnabled(true);
            hour2.setPaintFlags(0);

        } else if (time.equals("12:3014:15")) {
            hour3.setEnabled(true);
            hour3.setPaintFlags(0);
        } else {
            hour4.setEnabled(true);
            hour4.setPaintFlags(0);

        }

    }

    public String [] getRoomNames(List<Room> rooms){
        StringBuilder roomArray = new StringBuilder();
        for(Room r: rooms){
            roomArray.append(r.getName()).append(","); }
        return roomArray.toString().split(","); }


}
