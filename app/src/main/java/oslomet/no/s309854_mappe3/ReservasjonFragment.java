package oslomet.no.s309854_mappe3;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class ReservasjonFragment extends Fragment {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String bygning;
    private String rom;
    private String dato;
    private String tidspunkt;
    private RadioButton time1;
    private RadioButton time2;
    private RadioButton time3;
    private RadioButton time4;
    private Spinner bygningDropdown;
    private Spinner romDropdown;
    private Button datoBtn;
    private Button reserverBtn;
    private EditText navnEditView;
    private  RadioGroup radioGroup;

    public ReservasjonFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservasjon, container, false);

        // Init variabler
        datoBtn = view.findViewById(R.id.dato);
        datoBtn.setTransformationMethod(null);
        time1 = view.findViewById(R.id.time1);
        time2 = view.findViewById(R.id.time2);
        time3 = view.findViewById(R.id.time3);
        time4 = view.findViewById(R.id.time4);
        navnEditView = view.findViewById(R.id.navn);
        reserverBtn = view.findViewById(R.id.reserver_btn);
        bygningDropdown = view.findViewById(R.id.bygning);
        romDropdown = view.findViewById(R.id.rom);
        radioGroup = view.findViewById(R.id.radio);

        // Vis dropdown
        visDatoDropdown(datoBtn);
        visBygningDropdown(bygningDropdown);
        visRomDropdown(romDropdown);

        reserverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leggTilReservasjon(navnEditView.getText().toString(), bygningDropdown.getSelectedItem().toString(),
                        romDropdown.getSelectedItem().toString(), visDatoDropdown(datoBtn), tidspunkt);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch (checkedId) {
                    case R.id.time1:
                        tidspunkt = "08:30-10:15";
                        break;
                    case R.id.time2:
                        tidspunkt = "10:30-12:15";
                        break;
                    case R.id.time3:
                        tidspunkt = "12:30-14:15";
                        break;
                    case R.id.time4:
                        tidspunkt = "14:30-16:15";
                        break;
                }
            }
        });


        return view;
    }


    public String visDatoDropdown(final Button datoBtn) {


        datoBtn.setOnClickListener(new View.OnClickListener() {
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

                String date = month + "/" + day + "/" + year;
                dato = date;
                datoBtn.setText("Valgt dato: " + date);

                bygning = bygningDropdown.getSelectedItem().toString();
                rom = romDropdown.getSelectedItem().toString();
                Log.i("currentInfo:", bygning + ", " + rom + ", " + dato + ", " + tidspunkt);
                visledigeTimer(bygning, rom, dato);

            }
        };

        return dato;
    }

    public void leggTilReservasjon(String navn, String bygning, String rom,
                                   String dato, String tidspunkt) {
        Service service = new Service();
        ReservasjonListeFragment listeFragment = new ReservasjonListeFragment();
        String url = "http://student.cs.hioa.no/~s309854/jsonin.php?navn=" + navn + "&bygning=" + bygning
                + "&rom=" + rom + "&dato=" + dato + "&tidspunkt=" + tidspunkt;
        if (erSkjemaetValid(navn, bygning, rom, dato, tidspunkt)) {
            service.execute(url);
            visBekreftelseAlert();
            visAlleReservasjoner(listeFragment);
        } else {
            visErroAlert();
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


    public void visAlleReservasjoner(final Fragment fragment) {
        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        setFragment(fragment);
                    }
                }, 3000);
    }


    public boolean erSkjemaetValid(String navn, String bygning, String rom,
                                   String dato, String tidspunkt) {

        if (navn == null || navn.trim().equals("") || bygning.equals("Velg bygning")
                || rom.equals("Velg rom") || dato == null || tidspunkt == null) {
            return false;
        }
        return true;
    }

    public String visBygningDropdown(Spinner spinner) {
        final String[] bygning = new String[1];
        String[] bygninger = new String[]{"Velg bygning", "P32", "P35", "P46", "P48", "P52"};
        ArrayAdapter<String> bygningAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, bygninger);
        spinner.setAdapter(bygningAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bygning[0] = adapterView.getSelectedItem().toString();
                Log.i("currentInfo2:", "selected: + " + bygning[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return bygning[0];

    }

    public String visRomDropdown(Spinner spinner) {
        final String[] rom = new String[1];
        String[] romListe = new String[]{"Velg rom", "PH320", "PH350", "PH460", "PH480", "PH520"};
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



    public void visledigeTimer(String bygning, String rom, String dato) {
        KartAktivitet kartAktivitet = new KartAktivitet();
        ArrayList<Reservasjon> reservasjoner = kartAktivitet.hentReservasjoner();
        for (Reservasjon r : reservasjoner) {
            String tidspunkt = r.getFra() + r.getTil();
            if (r.getBygning().equals(bygning) && r.getRom().equals(rom) && r.getDato().equals(dato)) {
                deaktiverTimeValg(tidspunkt);

            }
            else{
                aktiverTimeValg(tidspunkt);
            }
        }
    }

    public void deaktiverTimeValg(String tidspunkt) {
        if (tidspunkt.equals("08:3010:15")) {
            time1.setPaintFlags(time1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            time1.setEnabled(false);


        } else if (tidspunkt.equals("10:3012:15")) {
            time2.setPaintFlags(time2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            time2.setEnabled(false);

        } else if (tidspunkt.equals("12:3014:15")) {
            time3.setPaintFlags(time3.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            time3.setEnabled(false);
        } else {
            time4.setPaintFlags(time4.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            time4.setEnabled(false);

        }

    }

    public void visErroAlert() {
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

    public void visBekreftelseAlert() {
        new CDialog(getContext()).createAlert("Reservert!",
                CDConstants.SUCCESS,   // Type of dialog
                CDConstants.LARGE)    //  size of dialog
                .setAnimation(CDConstants.SCALE_FROM_BOTTOM_TO_TOP)     //  Animation for enter/exit
                .setDuration(3000)   // in milliseconds
                .setTextSize(CDConstants.LARGE_TEXT_SIZE)  // CDConstants.LARGE_TEXT_SIZE, CDConstants.NORMAL_TEXT_SIZE
                .show();

    }

    public void aktiverTimeValg(String tidspunkt){
        if (tidspunkt.equals("08:3010:15")) {
            time1.setEnabled(true);
            time1.setPaintFlags(0);

        } else if (tidspunkt.equals("10:3012:15")) {
            time2.setEnabled(true);
            time2.setPaintFlags(0);

        } else if (tidspunkt.equals("12:3014:15")) {
            time3.setEnabled(true);
            time3.setPaintFlags(0);
        } else {
            time4.setEnabled(true);
            time4.setPaintFlags(0);

        }

    }


}
