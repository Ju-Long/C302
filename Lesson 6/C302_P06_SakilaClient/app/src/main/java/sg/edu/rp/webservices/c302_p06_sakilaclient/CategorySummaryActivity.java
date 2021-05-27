package sg.edu.rp.webservices.c302_p06_sakilaclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.*;
import com.loopj.android.http.*;

public class CategorySummaryActivity extends AppCompatActivity {

    ListView lvSummary;
    ArrayList<CategorySummary> categorySummaries;
    SummaryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_summary);

        lvSummary = findViewById(R.id.lvSummary);
        categorySummaries = new ArrayList<>();
        adapter = new SummaryAdapter(this, R.layout.summary_list, categorySummaries);
        lvSummary.setAdapter(adapter);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://10.0.2.2/c302_sakila/getCategorySummary.php", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject summary = (JSONObject) response.get(i);
                        CategorySummary cs = new CategorySummary(summary.getString("category"), summary.getInt("number_films"));
                        categorySummaries.add(cs);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {}
            }
        });
    }
}