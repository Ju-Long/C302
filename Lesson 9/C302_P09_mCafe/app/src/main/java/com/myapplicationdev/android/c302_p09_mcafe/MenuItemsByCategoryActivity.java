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

public class MenuItemsByCategoryActivity extends AppCompatActivity {

    ArrayList<MenuCategoryItem> list;
    ArrayAdapter<MenuCategoryItem> adapter;
    ListView lv;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_items_by_category);

        Intent intent = getIntent();
        lv = findViewById(R.id.menu_item_listview);
        list = new ArrayList<>();
        adapter = new ArrayAdapter<MenuCategoryItem>(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);

        preferences = getSharedPreferences("C302_P09", Context.MODE_PRIVATE);
        String loginId = preferences.getString("loginId", "");
        String apikey = preferences.getString("apikey", "");

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("loginId", loginId);
        params.add("apikey", apikey);

        client.post("http://10.0.2.2/C302_P09_mCafe/getMenuItemsByCategory.php?category_id="+intent.getStringExtra("category_id"), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                list.clear();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject item = (JSONObject) response.get(i);
                        list.add(new MenuCategoryItem(item.getString("menu_item_id"), item.getString("menu_item_category_id"), item.getString("menu_item_description"), item.getDouble("menu_item_unit_price")));

                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) { e.printStackTrace(); }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (!response.getBoolean("authenticate")) {
                        Toast.makeText(MenuItemsByCategoryActivity.this, "Authenticate Wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) { e.printStackTrace(); }
            }
        });

        lv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MenuItemsByCategoryActivity.this, MenuItemActivity.class);
                i.putExtra("item_id", list.get(position).getCategoryId());
                startActivity(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
            Intent intent = new Intent(MenuItemsByCategoryActivity.this, MenuItemActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}