package sg.edu.rp.webservices.c302_p06_sakilaclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FilmAdapter extends ArrayAdapter<Film> {
    private ArrayList<Film> films;
    private Context context;
    private TextView tvTitle, tvYear, tvRating;

    public FilmAdapter(Context context, int resource, ArrayList<Film> objects) {
        super(context, resource, objects);
        films = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.film_list, parent, false);

        tvTitle = rowView.findViewById(R.id.tvTitle);
        tvYear = rowView.findViewById(R.id.tvYear);
        tvTitle = rowView.findViewById(R.id.tvRating);

        Film currFilm = films.get(position);
        tvTitle.setText(currFilm.getTitle());
        tvYear.setText(String.valueOf(currFilm.getRelease_year()));
        tvRating.setText(currFilm.getRating());

        return rowView;
    }
}
