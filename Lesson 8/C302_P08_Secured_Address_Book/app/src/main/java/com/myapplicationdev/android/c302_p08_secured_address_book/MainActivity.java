package com.myapplicationdev.android.c302_p08_secured_address_book;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private ListView lvContact;
    private ArrayList<Contact> alContact;
    private ArrayAdapter<Contact> aaContact;

    // TODO (3) Declare loginId and apikey
    private int loginId;
    private String apikey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvContact = (ListView) findViewById(R.id.listViewContact);
        alContact = new ArrayList<Contact>();

        aaContact = new ContactAdapter(this, R.layout.contact_row, alContact);
        lvContact.setAdapter(aaContact);

        // TODO (4) Get loginId and apikey from the previous Intent
        Intent intent = getIntent();
        apikey = intent.getStringExtra("apikey");
        loginId = intent.getIntExtra("loginId", 0);

        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Contact selectedContact = alContact.get(position);

                // TODO (7) When a contact is selected, create an Intent to View Contact Details
                // Put the following into intent:- contact_id, loginId, apikey
                Intent i = new Intent(MainActivity.this, ViewContactDetailsActivity.class);
                i.putExtra("contact_id", selectedContact.getContactId());
                i.putExtra("loginId", loginId);
                i.putExtra("apikey", apikey);
                startActivity(i);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        alContact.clear();

        // TODO (5) Refresh the main activity with the latest list of contacts by calling getListOfContacts.php
        // What is the web service URL?
        // What is the HTTP method?
        // What parameters need to be provided?
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("apikey", apikey);
        params.add("loginId", String.valueOf(loginId));

        client.post("http://10.0.2.2/C302_P08_SecuredCloudAddressBook/getListOfContacts.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = (JSONObject) response.get(i);
                        alContact.add(new Contact(object.getInt("id"), object.getString("firstname"), object.getString("lastname"), object.getString("Mobile")));
                    }
                    aaContact.notifyDataSetChanged();
                } catch (JSONException e) { e.printStackTrace(); }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Toast.makeText(MainActivity.this, "Failed to authenticate the user, please try login again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO (6) Using AsyncHttpClient for getListOfContacts.php, get all contacts from the results and show in the list

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menu_add) {

            // TODO (8) Create an Intent to Create Contact
            // Put the following into intent:- loginId, apikey
            Intent intent = new Intent(MainActivity.this, CreateContactActivity.class);
            intent.putExtra("loginId", loginId);
            intent.putExtra("apikey", apikey);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
