package com.myapplicationdev.android.c302_magic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class CardActivity_19013345 extends AppCompatActivity {

    SharedPreferences preferences;
    ListView card_listview;
    ArrayList<Card> cards;
    CardAdapter adapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_19013345);

        preferences = getSharedPreferences("C302_Magic", Context.MODE_PRIVATE);
        intent = getIntent();
        intent.getIntExtra("colour_id", 0);
        card_listview = findViewById(R.id.card_listview);
        cards = new ArrayList<>();
        adapter = new CardAdapter(this, R.layout.card_row, cards);
        card_listview.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        intent = getIntent();
        RequestParams params = new RequestParams();
        params.add("user_id", String.valueOf(preferences.getInt("user_id", 0)));
        params.add("apikey", preferences.getString("apikey", ""));
        params.add("colour_id", String.valueOf(intent.getIntExtra("colour_id", 0)));

        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://192.168.1.154/C302/C302_Magic/19013345_getCardsByColour.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    switch (response.getString("output")) {
                        case "success":
                            JSONArray list_of_cards = response.getJSONArray("result");
                            cards.clear();
                            for (int i = 0; i < list_of_cards.length(); i++) {
                                JSONObject card = (JSONObject) list_of_cards.get(i);
                                cards.add(new Card(card.getInt("cardId"), card.getString("cardName"), card.getInt("colourId"), card.getInt("typeId"), card.getDouble("price"), card.getInt("quantity")));
                            }
                            adapter.notifyDataSetChanged();
                            break;

                        default:
                            Toast.makeText(CardActivity_19013345.this, response.getString("output"), Toast.LENGTH_SHORT).show();
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
                intent = new Intent(CardActivity_19013345.this, CreateCardActivity_19013345.class);
                break;

            case R.id.cards_by_colour_item:
                intent = new Intent(CardActivity_19013345.this, MainActivity.class);
                break;

            default:
                intent = new Intent(CardActivity_19013345.this, LoginActivity.class);
        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}