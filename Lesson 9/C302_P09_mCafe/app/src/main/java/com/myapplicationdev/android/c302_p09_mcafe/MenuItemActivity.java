package com.myapplicationdev.android.c302_p09_mcafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MenuItemActivity extends AppCompatActivity {

    Spinner spinner;
    EditText item_description, item_price;
    Button add, update, delete;
    ArrayList<MenuCategory> arrayList;
    ArrayAdapter<MenuCategory> adapter;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);

        preferences = getSharedPreferences("C302_P09", Context.MODE_PRIVATE);
        String loginId = preferences.getString("loginId", "");
        String apikey = preferences.getString("apikey", "");

        spinner = findViewById(R.id.item_category_spinner);
        item_description = findViewById(R.id.item_description_edittext);
        item_price = findViewById(R.id.item_price_edittext);
        add = findViewById(R.id.item_add_btn);
        update = findViewById(R.id.item_update_btn);
        delete = findViewById(R.id.item_delete_btn);
        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arrayList);

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("loginId", loginId);
        params.add("apikey", apikey);

        client.post("http://10.0.2.2/C302_P09_mCafe/getMenuCategories.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    arrayList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject category = (JSONObject) response.get(i);
                        arrayList.add(new MenuCategory(category.getString("menu_item_category_id"), category.getString("menu_item_category_description")));
                    }
                    adapter.notifyDataSetChanged();
                    spinner.setSelection(0);
                } catch (JSONException e) { e.printStackTrace(); }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (!response.getBoolean("authenticate")) {
                        Toast.makeText(MenuItemActivity.this, "Authenticate Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) { e.printStackTrace(); }
            }
        });

        add.setOnClickListener(v -> {
            if (item_description.getText().toString().isEmpty() || item_price.getText().toString().isEmpty()) {
                Toast.makeText(MenuItemActivity.this, "Empty Value Detected", Toast.LENGTH_SHORT).show();
            } else {
                params.add("category_id", arrayList.get(spinner.getSelectedItemPosition()).getCategoryId());
                params.add("description", item_description.getText().toString());
                params.add("price", item_price.getText().toString());
                client.post("http://10.0.2.2/C302_P09_mCafe/addMenuItem.php", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            if (!response.getBoolean("authenticate")) {
                                Toast.makeText(MenuItemActivity.this, "Authenticate Error", Toast.LENGTH_SHORT).show();
                            } else {
                                if (response.getString("output").equals("success")) {
                                    Toast.makeText(MenuItemActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(MenuItemActivity.this, "Failed to add", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (JSONException e) { e.printStackTrace(); }
                    }
                });
            }
        });

        update.setOnClickListener(v -> {
            Intent intent = getIntent();
            if (item_description.getText().toString().isEmpty() || item_price.getText().toString().isEmpty() || intent.getStringExtra("item_id").isEmpty()) {
                Toast.makeText(MenuItemActivity.this, "Empty Value Detected", Toast.LENGTH_SHORT).show();
            } else {
                params.add("item_id", intent.getStringExtra("item_id"));
                params.add("category_id", arrayList.get(spinner.getSelectedItemPosition()).getCategoryId());
                params.add("description", item_description.getText().toString());
                params.add("price", item_price.getText().toString());
                client.post("http://10.0.2.2/C302_P09_mCafe/updateMenuItemById.php", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            if (!response.getBoolean("authenticate")) {
                                Toast.makeText(MenuItemActivity.this, "Authenticate Error", Toast.LENGTH_SHORT).show();
                            } else {
                                if (response.getString("output").equals("success")) {
                                    Toast.makeText(MenuItemActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(MenuItemActivity.this, "Failed to update", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (JSONException e) { e.printStackTrace(); }
                    }
                });
            }
        });

        delete.setOnClickListener(v -> {
            Intent intent = getIntent();
            if (intent.getStringExtra("item_id").isEmpty()) {
                Toast.makeText(MenuItemActivity.this, "No item selected", Toast.LENGTH_SHORT).show();
            } else {
                params.add("item_id", intent.getStringExtra("item_id"));
                client.post("http://10.0.2.2/C302_P09_mCafe/deleteMenuItemById.php", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            if (!response.getBoolean("authenticate")) {
                                Toast.makeText(MenuItemActivity.this, "Authenticate Error", Toast.LENGTH_SHORT).show();
                            } else {
                                if (response.getString("output").equals("success")) {
                                    Toast.makeText(MenuItemActivity.this, "Successfully deleted", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(MenuItemActivity.this, "Failed to delete", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (JSONException e) { e.printStackTrace(); }
                    }
                });
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
            Intent intent = new Intent(MenuItemActivity.this, MenuItemActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}