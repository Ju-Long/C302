package sg.edu.rp.webservices.c302_p06_sakilaclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FilmLocationAdapter extends ArrayAdapter<FilmLocation> {
    private ArrayList<FilmLocation> filmLocations;
    private Context context;
    private TextView tvAddress, tvPostalCode, tvPhone, tvCity, tvCountry;

    public FilmLocationAdapter(Context context, int resource, ArrayList<FilmLocation> objects) {
        super(context, resource, objects);
        filmLocations = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.search_list, parent, false);

        tvAddress = rowView.findViewById(R.id.tvSearchAddress);
        tvPostalCode = rowView.findViewById(R.id.tvSearchPostalCode);
        tvPhone = rowView.findViewById(R.id.tvSearchPhone);
        tvCity = rowView.findViewById(R.id.tvSearchCity);
        tvCountry = rowView.findViewById(R.id.tvSearchCountry);

        FilmLocation currLocation = filmLocations.get(position);

        tvAddress.setText(currLocation.getAddress());
        tvPostalCode.setText(String.valueOf(currLocation.getPostal_code()));
        tvPhone.setText(String.valueOf(currLocation.getPhone()));
        tvCity.setText(currLocation.getCity());
        tvCountry.setText(currLocation.getCountry());

        return rowView;
    }
}
