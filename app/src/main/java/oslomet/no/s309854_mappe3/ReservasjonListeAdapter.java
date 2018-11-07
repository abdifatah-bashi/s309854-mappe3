package oslomet.no.s309854_mappe3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class ReservasjonListeAdapter extends ArrayAdapter<Reservasjon> {

    private Context context;
    private int resource;
    private ArrayList<Reservasjon> reservasjonArrayList;

    /**
     * Holder variabler i View-et
     */
    private static class ViewHolder {
        TextView navn;
        TextView bygning;
        TextView rom;
        TextView dato;
        TextView fra;
        TextView til;

    }

    // Konstruktør
    public ReservasjonListeAdapter(Context context, int resource, ArrayList<Reservasjon> reservasjonArrayList) {

        super(context, resource, reservasjonArrayList);
        this.reservasjonArrayList = reservasjonArrayList;
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        String navn = getItem(position).getNavn();
        String bygning = getItem(position).getBygning();
        String rom = getItem(position).getRom();
        String dato = getItem(position).getDato();
        String fra = getItem(position).getFra();
        String til = getItem(position).getTil();
        final Reservasjon venn = new Reservasjon
                (navn, bygning, rom, dato, fra, til );

        //ViewHolder object
        ViewHolder holder;


        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();

            holder.navn = convertView.findViewById(R.id.navn);
            holder.bygning = convertView.findViewById(R.id.bygning);
            holder.rom = convertView.findViewById(R.id.rom);
            holder.dato = convertView.findViewById(R.id.dato);
            holder.fra = convertView.findViewById(R.id.fra);
            holder.til = convertView.findViewById(R.id.til);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.navn.setText("Navn: " + navn);
        holder.bygning.setText("Bygning: " + bygning);
        holder.rom.setText("Rom: " + rom);
        holder.dato.setText("Dato: " + dato);
        holder.fra.setText("Fra: " + fra);
        holder.til.setText("Til: " + til);
        return convertView;
    }

    // Hjelp metoder
    public String toString(List<String> venner) {
        StringBuilder builder = new StringBuilder();
        for (String v : venner) {
            builder.append(v).append("\n");
        }
        return builder.toString();
    }

}