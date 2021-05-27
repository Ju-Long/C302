package sg.edu.rp.webservices.c302_p06_sakilaclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.*;

public class MainActivity extends AppCompatActivity {

    private ListView lvCategories;
    ArrayList<Category> alCategories = new ArrayList<Category>();
    ArrayAdapter<Category> aaCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCategories = (ListView) findViewById(R.id.listViewCategories);
        aaCategories = new ArrayAdapter<Category>(this, android.R.layout.simple_list_item_1, alCategories);
        lvCategories.setAdapter(aaCategories);

		//TODO: Task 1: Consume getCategories.php using AsyncHttpClient
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://10.0.2.2/c302_sakila/getCategories.php", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject category = (JSONObject) response.get(i);
                        Category c = new Category(category.getInt("category_id"), category.getString("name"));
                        alCategories.add(c);
                    }
                } catch (JSONException e) {
                }
                aaCategories.notifyDataSetChanged();
            }
        });

        lvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Category c = alCategories.get(i);  // Get the selected Category
                Intent intent = new Intent(MainActivity.this, ViewFilmsActivity.class);
                intent.putExtra("category", c);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ViewCategorySummary:
                Intent i = new Intent(MainActivity.this, CategorySummaryActivity.class);
                startActivity(i);
                return true;
            case R.id.SearchRentalLocation:
                Intent intent = new Intent(MainActivity.this, SearchFilmsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
