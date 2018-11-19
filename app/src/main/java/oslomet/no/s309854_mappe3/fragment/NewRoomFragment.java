package oslomet.no.s309854_mappe3.fragment;


import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.circulardialog.CDialog;
import com.example.circulardialog.extras.CDConstants;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;

import java.util.Map;

import oslomet.no.s309854_mappe3.MapActivity;
import oslomet.no.s309854_mappe3.R;
import oslomet.no.s309854_mappe3.model.Room;
import oslomet.no.s309854_mappe3.service.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewRoomFragment extends Fragment {
    EditText nameEditText;
    EditText detailsEditText;
    EditText latitudeEditText;
    EditText longittudeEditText;



    public NewRoomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_room2, container, false);
        // Init variables
        nameEditText = view.findViewById(R.id.room_name);
       detailsEditText = view.findViewById(R.id.room_details);
       latitudeEditText = view.findViewById(R.id.latitude);
       longittudeEditText = view.findViewById(R.id.longitude);
        final Button addBtn = view.findViewById(R.id.add_btn);
        addBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addRoom(nameEditText.getText().toString(), detailsEditText.getText().toString(),
                        latitudeEditText.getText().toString(), longittudeEditText.getText().toString());
            }
        } );
        return view;
    }

    public void addRoom(String name, String details,
                        String latitude, String longitude){


        if(!isFormValid(name, details, latitude, longitude)){
            String message = "Vennligst fyll alle feltene i skjemaet!";
            showErrorAlert(message);
        }
       else if(isRoomExist(name)){
            String message = "Rom finnes allerede!";
            showErrorAlert(message);
        }
        else if (isFormValid(name, details, latitude, longitude)) {
            String url = "http://student.cs.hioa.no/~s309854/roomin.php?name=" + name + "&details=" + details
                    + "&latitude=" + latitude + "&longitude=" + longitude ;
            oslomet.no.s309854_mappe3.service.Service service = new oslomet.no.s309854_mappe3.service.Service();
            service.execute(url);
            showConfirmationAlert();
            changeToMapActivity();
        }
    }


    // Help methods
    public void showErrorAlert(String message) {
        new FancyAlertDialog.Builder((Activity) getContext())
                .setTitle(message)
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

    public boolean isFormValid(String name, String details,
                               String latitude, String longitude) {
        String nameRegEx = "(?i)(^[a-z]+)[a-z .,-]((?! .,-)$){1,20}$";
        if (name == null || name.trim().isEmpty() || !name.matches(nameRegEx)
                || details == null || details.trim().isEmpty()
                || latitude == null || latitude.trim().isEmpty()
                || longitude == null || longitude.trim().isEmpty()){
            return false;
        }
        return true;
    }

    public boolean isRoomExist(String roomName){
        MapActivity activity = new MapActivity();
        for(Room r: activity.getRooms() ){
            if(r.getName().equals(roomName)) return true;
        }
        return false;
    }


    public void showConfirmationAlert() {
        new CDialog(getContext()).createAlert("La til!",
                CDConstants.SUCCESS,   // Type of dialog
                CDConstants.LARGE)    //  size of dialog
                .setAnimation(CDConstants.SCALE_FROM_BOTTOM_TO_TOP)     //  Animation for enter/exit
                .setDuration(3000)   // in milliseconds
                .setTextSize(CDConstants.LARGE_TEXT_SIZE)  // CDConstants.LARGE_TEXT_SIZE, CDConstants.NORMAL_TEXT_SIZE
                .show();

    }

    public void changeToMapActivity(){
        new Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            Intent intent = new Intent(getContext(), MapActivity.class);
                            startActivity(intent);
                        }
                    }, 3000);
        }


}
