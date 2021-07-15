package com.myapplicationdev.android.c302_p09_mcafe;

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

    private ListView listView;
    private ArrayAdapter<MenuCategory> adapter;
    private ArrayList<MenuCategory> list;
    private AsyncHttpClient client;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listViewMenuCategories);
        list = new ArrayList<MenuCategory>();
        adapter = new ArrayAdapter<MenuCategory>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        client = new AsyncHttpClient();

        //TODO: read loginId and apiKey from SharedPreferences
        preferences = getSharedPreferences("C302_P09", Context.MODE_PRIVATE);
        String loginId = preferences.getString("loginId", "");
        String apikey = preferences.getString("apikey", "");

        // TODO: if loginId and apikey is empty, go back to LoginActivity
        if (loginId.isEmpty() || apikey.isEmpty()) {
            finish();
        }

        //TODO: Point X - call getMenuCategories.php to populate the list view
        RequestParams params = new RequestParams();
        params.add("loginId", loginId);
        params.add("apikey", apikey);

        client.post("http://10.0.2.2/C302_P09_mCafe/getMenuCategories.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    list.clear();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject category = (JSONObject) response.get(i);
                        list.add(new MenuCategory(category.getString("menu_item_category_id"), category.getString("menu_item_category_description")));
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) { e.printStackTrace(); }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (!response.getBoolean("authenticate")) {
                        Toast.makeText(MainActivity.this, "Authenticate Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) { e.printStackTrace(); }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MenuCategory selected = list.get(position);

                //TODO: make Intent to DisplayMenuItemsActivity passing the categoryId
                Intent intent = new Intent(MainActivity.this, MenuItemsByCategoryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        if (preferences.getString("role", "").equals("admin")) {
            getMenuInflater().inflate(R.menu.submain, menu);
        } else {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            // TODO: Clear SharedPreferences
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            // TODO: Redirect back to login screen
            finish();
            return true;
        } else if (id == R.id.menu_addmenuitem) {
            Intent intent = new Intent(MainActivity.this, MenuItemActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}