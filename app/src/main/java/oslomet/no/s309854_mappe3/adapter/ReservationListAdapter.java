package oslomet.no.s309854_mappe3.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import oslomet.no.s309854_mappe3.R;
import oslomet.no.s309854_mappe3.model.Reservation;


public class ReservationListAdapter extends ArrayAdapter<Reservation> {

    private Context context;
    private int resource;
    private List<Reservation> reservationArrayList;

    /**
     * Holder variabler i View-et
     */
    private static class ViewHolder {
        TextView firstname;
        TextView lastname;
        TextView room;
        TextView date;
        TextView time;

    }

    // Konstruktør
    public ReservationListAdapter(Context context, int resource, List<Reservation> reservationArrayList) {

        super(context, resource, reservationArrayList);
        this.reservationArrayList = reservationArrayList;
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        String firstName = getItem(position).getFirstName();
        String lastName = getItem(position).getLastName();
        String room = getItem(position).getRoom();
        String date = getItem(position).getDate();
        String time = getItem(position).getTime();
        final Reservation venn = new Reservation
                (firstName, lastName, room, date, time );

        //ViewHolder object
        ViewHolder holder;


        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();

            holder.firstname = convertView.findViewById(R.id.firstname);
            holder.lastname = convertView.findViewById(R.id.lastname);
            holder.room = convertView.findViewById(R.id.room);
            holder.date = convertView.findViewById(R.id.date);
            holder.time = convertView.findViewById(R.id.time);



            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.firstname.setText("Fornavn: " + firstName);
        holder.lastname.setText("Etternavn: " + lastName);
        holder.room.setText("Rom: " + room);
        holder.date.setText("Dato: " + date);
        holder.time.setText("Tidspunkt: " + time);

        return convertView;
    }

}