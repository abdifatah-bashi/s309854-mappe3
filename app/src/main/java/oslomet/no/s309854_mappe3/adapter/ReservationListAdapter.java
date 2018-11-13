package oslomet.no.s309854_mappe3.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import oslomet.no.s309854_mappe3.R;
import oslomet.no.s309854_mappe3.model.Reservation;


public class ReservationListAdapter extends ArrayAdapter<Reservation> {

    private Context context;
    private int resource;
    private ArrayList<Reservation> reservationArrayList;

    /**
     * Holder variabler i View-et
     */
    private static class ViewHolder {
        TextView name;
        TextView building;
        TextView room;
        TextView date;
        TextView from;
        TextView to;

    }

    // Konstruktør
    public ReservationListAdapter(Context context, int resource, ArrayList<Reservation> reservationArrayList) {

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
        String from = getItem(position).getFrom();
        String to = getItem(position).getTo();
        final Reservation venn = new Reservation
                (firstName, lastName, room, date, from, to );

        //ViewHolder object
        ViewHolder holder;


        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();

            holder.name = convertView.findViewById(R.id.firstname);
            holder.building = convertView.findViewById(R.id.lastname);
            holder.room = convertView.findViewById(R.id.room);
            holder.date = convertView.findViewById(R.id.date);
            holder.from = convertView.findViewById(R.id.from);
            holder.to = convertView.findViewById(R.id.to);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.name.setText("Fornavn: " + firstName);
        holder.building.setText("Etternavn: " + lastName);
        holder.room.setText("Rom: " + room);
        holder.date.setText("Dato: " + date);
        holder.from.setText("Fra: " + from);
        holder.to.setText("Til: " + to);
        return convertView;
    }

}