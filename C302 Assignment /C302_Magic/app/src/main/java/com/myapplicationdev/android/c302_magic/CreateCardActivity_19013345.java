package com.myapplicationdev.android.c302_magic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

public class CreateCardActivity_19013345 extends AppCompatActivity {

    SharedPreferences preferences;
    EditText card_name, card_price, card_quantity;
    Spinner colour_spinner, type_spinner;
    Button add;
    ArrayList<String> colour_names, type_names;
    ArrayList<Colour> colours;
    ArrayList<Type> types;
    ArrayAdapter<String> colour_adapter, type_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card_19013345);

        preferences = getSharedPreferences("C302_Magic", Context.MODE_PRIVATE);
        card_name = findViewById(R.id.card_name_edittext);
        colour_spinner = findViewById(R.id.colour_spinner);
        type_spinner = findViewById(R.id.type_spinner);
        card_price = findViewById(R.id.card_price_edittext);
        card_quantity = findViewById(R.id.card_quantity_edittext);
        add = findViewById(R.id.add_button);

        colours = new ArrayList<>();
        colour_names = new ArrayList<>();
        types = new ArrayList<>();
        type_names = new ArrayList<>();
        colour_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colour_names);
        type_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, type_names);

        colour_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        colour_spinner.setAdapter(colour_adapter);
        type_spinner.setAdapter(type_adapter);

        add.setOnClickListener(v -> {
            boolean proceed = true;
            if (card_name.getText().toString().trim().isEmpty()) {
                Toast.makeText(CreateCardActivity_19013345.this, "Card name is not filled", Toast.LENGTH_SHORT).show();
                proceed = false;
            } if (card_price.getText().toString().trim().isEmpty() || Double.parseDouble(card_price.getText().toString().trim()) < 0) {
                Toast.makeText(CreateCardActivity_19013345.this, "Price is not filled or less than 0", Toast.LENGTH_SHORT).show();
                proceed = false;
            } if (card_quantity.getText().toString().trim().isEmpty() || Integer.parseInt(card_quantity.getText().toString().trim()) < 0) {
                Toast.makeText(CreateCardActivity_19013345.this, "Quantity is not filled or less than 0", Toast.LENGTH_SHORT).show();
                proceed = false;
            }

            if (proceed) {
                RequestParams params = new RequestParams();
                params.add("user_id", String.valueOf(preferences.getInt("user_id", 0)));
                params.add("apikey", preferences.getString("apikey", ""));
                params.add("card_name", card_name.getText().toString().trim());
                params.add("colour_id", String.valueOf(colours.get(colour_spinner.getSelectedItemPosition()).getColour_id()));
                params.add("type_id", String.valueOf(types.get(type_spinner.getSelectedItemPosition()).getType_id()));
                params.add("price", card_price.getText().toString().trim());
                params.add("amount", card_quantity.getText().toString().trim());

                AsyncHttpClient client = new AsyncHttpClient();
                client.post("http://192.168.1.154/C302/C302_Magic/19013345_createCard.php", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            switch (response.getString("output")) {
                                case "success":
                                    Toast.makeText(CreateCardActivity_19013345.this, "Successfully inserted", Toast.LENGTH_SHORT).show();
                                    finish();
                                    break;

                                default:
                                    Toast.makeText(CreateCardActivity_19013345.this, response.getString("output"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) { e.printStackTrace(); }
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        RequestParams colour_params = new RequestParams();
        colour_params.add("user_id", String.valueOf(preferences.getInt("user_id", 0)));
        colour_params.add("apikey", preferences.getString("apikey", ""));

        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://192.168.1.154/C302/C302_Magic/19013345_getColours.php", colour_params, new JsonHttpResponseHandler() {
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
                            colour_adapter.notifyDataSetChanged();
                            colour_spinner.setSelection(0);
                            break;

                        default:
                            Toast.makeText(CreateCardActivity_19013345.this, response.getString("output"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) { e.printStackTrace(); }
            }
        });

        RequestParams type_params = new RequestParams();
        type_params.add("user_id", String.valueOf(preferences.getInt("user_id", 0)));
        type_params.add("apikey", preferences.getString("apikey", ""));

        client.post("http://192.168.1.154/C302/C302_Magic/get_all_type.php", type_params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    switch (response.getString("output")) {
                        case "success":
                            JSONArray type_list = response.getJSONArray("result");
                            types.clear();
                            type_names.clear();
                            for (int i = 0; i < type_list.length(); i++) {
                                JSONObject type = (JSONObject) type_list.get(i);
                                types.add(new Type(type.getInt("typeId"), type.getString("typeName")));
                                type_names.add(type.getString("typeName"));
                            }
                            type_adapter.notifyDataSetChanged();
                            type_spinner.setSelection(0);
                            break;

                        default:
                            Toast.makeText(CreateCardActivity_19013345.this, response.getString("output"), Toast.LENGTH_SHORT).show();
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
                intent = new Intent(CreateCardActivity_19013345.this, CreateCardActivity_19013345.class);
                break;

            case R.id.cards_by_colour_item:
                intent = new Intent(CreateCardActivity_19013345.this, MainActivity.class);
                break;

            default:
                intent = new Intent(CreateCardActivity_19013345.this, LoginActivity.class);
        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}