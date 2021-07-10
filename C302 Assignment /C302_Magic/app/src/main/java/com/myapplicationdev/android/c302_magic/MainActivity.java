package com.myapplicationdev.android.c302_magic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    ArrayList<Colour> colours;
    ArrayList<String> colour_names;
    ArrayAdapter<String> adapter;
    ListView colour_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("C302_Magic", Context.MODE_PRIVATE);
        colour_listview = findViewById(R.id.colour_listview);
        colours = new ArrayList<>();
        colour_names = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, colour_names);
        colour_listview.setAdapter(adapter);

        colour_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, CardActivity_19013345.class);
                intent.putExtra("colour_id", colours.get(position).getColour_id());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        RequestParams params = new RequestParams();
        params.add("user_id", String.valueOf(preferences.getInt("user_id", 0)));
        params.add("apikey", preferences.getString("apikey", ""));

        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://192.168.1.154/C302/C302_Magic/19013345_getColours.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    switch (response.getString("output")) {
                        case "success":
                            JSONArray colour_list = response.getJSONArray("result");
                            colours.clear();
                            colour_names.clear();
                            for (int i = 0; i < colour_list.length(); i++) {
                                JSONObject colour = (JSONObject) colour_list.get(i);
                                colours.add(new Colour(colour.getInt("colourId"), colour.getString("colourName")));
                                colour_names.add(colour.getString("colourName"));
                            }
                            adapter.notifyDataSetChanged();
                            break;

                        default:
                            Toast.makeText(MainActivity.this, response.getString("output"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) { e.printStackTrace(); }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        switch (preferences.getString("role", "customer")) {
            case "admin":
                menu.setGroupVisible(R.id.admin_group, true);
                break;

            default:
                menu.setGroupVisible(R.id.admin_group, false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.add_card_item:
                intent = new Intent(MainActivity.this, CreateCardActivity_19013345.class);
                break;

            case R.id.cards_by_colour_item:
                intent = new Intent(MainActivity.this, MainActivity.class);
                break;

            default:
                intent = new Intent(MainActivity.this, LoginActivity.class);
        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}