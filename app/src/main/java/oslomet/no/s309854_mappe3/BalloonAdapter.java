package oslomet.no.s309854_mappe3;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class BalloonAdapter implements InfoWindowAdapter {
    LayoutInflater inflater = null;
    private TextView textViewTitle;

    public BalloonAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View v = inflater.inflate(R.layout.balloon, null);
        if (marker != null) {
            textViewTitle = (TextView) v.findViewById(R.id.textViewTitle);
            String snippet = marker.getSnippet();
            textViewTitle.setText("Reservasjon \n" + snippet);
        }
        return (v);
    }

    @Override
    public View getInfoContents(Marker marker) {
        return (null);
    }
}