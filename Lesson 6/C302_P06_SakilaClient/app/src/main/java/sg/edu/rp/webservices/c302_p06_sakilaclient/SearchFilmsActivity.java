package sg.edu.rp.webservices.c302_p06_sakilaclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.*;
import com.loopj.android.http.*;

public class SearchFilmsActivity extends AppCompatActivity {
    SearchView sv;
    TextView tvTitle, tvDescription, tvYear, tvRating;
    ListView lv;
    ArrayList<FilmLocation> filmLocations;
    FilmLocationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_films);

        sv = findViewById(R.id.sv);
        tvTitle = findViewById(R.id.tvSearchTitle);
        tvDescription = findViewById(R.id.tvSearchDescription);
        tvYear = findViewById(R.id.tvSearchYear);
        tvRating = findViewById(R.id.tvSearchRating);
        lv = findViewById(R.id.lvSearch);
        filmLocations = new ArrayList<>();
        adapter = new FilmLocationAdapter(this, R.layout.search_list, filmLocations);
        lv.setAdapter(adapter);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                RequestParams params = new RequestParams("id", newText);
                AsyncHttpClient client = new AsyncHttpClient();
                client.get("http://10.0.2.2/C302_sakila/getRentalLocationsByFilmId.php", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Film film = new Film(response.getString("title"), response.getString("description"), response.getInt("release_year"), response.getString("rating"));
                            JSONArray locations = (JSONArray) response.get("stores");
                            for (int i = 0; i < locations.length(); i++) {
                                JSONObject f = (JSONObject) locations.get(i);
                                FilmLocation fl = new FilmLocation(f.getString("address"), f.getInt("postal_code"), f.getInt("phone"), f.getString("city"), f.getString("country"));
                                filmLocations.add(fl);
                            }
                            tvTitle.setText(film.getTitle());
                            tvDescription.setText(film.getDescription());
                            tvYear.setText(String.valueOf(film.getRelease_year()));
                            tvRating.setText(film.getRating());
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {}
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                RequestParams params = new RequestParams("id", query);
                AsyncHttpClient client = new AsyncHttpClient();
                client.get("http://10.0.2.2/C302_sakila/getRentalLocationsByFilmId.php", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Film film = new Film(response.getString("title"), response.getString("description"), response.getInt("release_year"), response.getString("rating"));
                            JSONArray locations = (JSONArray) response.get("stores");
                            for (int i = 0; i < locations.length(); i++) {
                                JSONObject f = (JSONObject) locations.get(i);
                                FilmLocation fl = new FilmLocation(f.getString("address"), f.getInt("postal_code"), f.getInt("phone"), f.getString("city"), f.getString("country"));
                                filmLocations.add(fl);
                            }
                            tvTitle.setText(film.getTitle());
                            tvDescription.setText(film.getDescription());
                            tvYear.setText(String.valueOf(film.getRelease_year()));
                            tvRating.setText(film.getRating());
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {}
                    }
                });
                return false;
            }
        });

    }
}