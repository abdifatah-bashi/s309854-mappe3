package oslomet.no.s309854_mappe3.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

import oslomet.no.s309854_mappe3.R;

public class MapMarkerViewAdapter implements InfoWindowAdapter {
    LayoutInflater inflater = null;
    private TextView textViewTitle;

    public MapMarkerViewAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View v = inflater.inflate(R.layout.balloon, null);
        if (marker != null) {
            textViewTitle = (TextView) v.findViewById(R.id.textViewTitle);
            String snippet = marker.getSnippet();

            if(snippet.trim().isEmpty() ){
                textViewTitle.setText("Ingen reservasjon i dag!");
            } else {
                textViewTitle.setText("Dagens reservasjoner: \n  "+snippet);
            }

        }
        return (v);
    }

    @Override
    public View getInfoContents(Marker marker) {
        return (null);
    }
}