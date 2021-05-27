package sg.edu.rp.webservices.c302_p06_sakilaclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.*;
import com.loopj.android.http.*;

public class ViewFilmsActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<Film> films;
    FilmAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_films);

        lv = findViewById(R.id.lvFilms);
        films = new ArrayList<>();
        adapter = new FilmAdapter(this, R.layout.film_list, films);
        lv.setAdapter(adapter);

        Intent i = getIntent();
        Category c = (Category) i.getSerializableExtra("category");

        RequestParams params = new RequestParams("id", c.getId());
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://10.0.2.2/c302_sakila/getFilmsByCategoryId.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject f = (JSONObject) response.get(i);
                        Film film = new Film(f.getString("title"), f.getString("description"), f.getInt("release_year"), f.getString("rating"));
                        films.add(film);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {}
            }
        });
    }
}