package com.myapplicationdev.android.c302_p07_addressbook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private ListView lvContact;
    private ArrayList<Contact> alContact;
    private ArrayAdapter<Contact> aaContact;
    private AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvContact = (ListView) findViewById(R.id.listViewContact);
        client = new AsyncHttpClient();
    }

    //refresh with latest contact data whenever this activity resumes
    @Override
    protected void onResume() {
        super.onResume();

		alContact = new ArrayList<>();

		client.get("http://10.0.2.2/C302_P07/getListOfContacts.php", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    Log.i("JSON Results", response.toString());

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObj = response.getJSONObject(i);

                        int contactId = jsonObj.getInt("id");
                        String firstname = jsonObj.getString("firstname");
                        String lastname = jsonObj.getString("lastname");
                        String mobile = jsonObj.getString("mobile");

                        Contact contact = new Contact(contactId, firstname, lastname, mobile);
                        alContact.add(contact);
                    }
                } catch (JSONException e) { e.printStackTrace(); }

                aaContact = new ContactAdapter(getApplicationContext(), R.layout.contact_row, alContact);
                lvContact.setAdapter(aaContact);

                lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Contact selectContact = alContact.get(position);
                        Intent i = new Intent(getBaseContext(), ViewContactDetailsActivity.class);
                        i.putExtra("contact_id", selectContact.getContactId());
                        startActivity(i);
                    }
                });

            }
        });
    }//end onResume


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
            Intent intent = new Intent(getApplicationContext(), CreateContactActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}