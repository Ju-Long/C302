package com.myapplicationdev.android.c302_p08_secured_address_book;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ViewContactDetailsActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etMobile;
    private Button btnUpdate, btnDelete;
    private int contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact_details);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etMobile = findViewById(R.id.etMobile);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        Intent intent = getIntent();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(etFirstName.getText().toString().trim().isEmpty() && etLastName.getText().toString().trim().isEmpty() && etMobile.getText().toString().trim().isEmpty())) {
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.add("id", intent.getStringExtra("contact_id"));
                    params.add("FirstName", etFirstName.getText().toString().trim());
                    params.add("LastName", etLastName.getText().toString().trim());
                    params.add("Mobile", etMobile.getText().toString().trim());
                    params.add("loginId", String.valueOf(intent.getIntExtra("loginId", 0)));
                    params.add("apikey", intent.getStringExtra("apikey"));

                    client.post("http://10.0.2.2/C302_P08_SecuredCloudAddressBook/updateContact.php", params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {
                                if (response.getBoolean("authorized"))
                                    switch (response.getInt("status")) {
                                        case 1:
                                            Toast.makeText(ViewContactDetailsActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                            finish();
                                            break;

                                        default:
                                            Toast.makeText(ViewContactDetailsActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                    }
                                else
                                    Toast.makeText(ViewContactDetailsActivity.this, response.getString("reason"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) { e.printStackTrace(); }
                        }
                    });
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.add("id", intent.getStringExtra("contact_id"));
                params.add("loginId", String.valueOf(intent.getIntExtra("loginId", 0)));
                params.add("apikey", intent.getStringExtra("apikey"));

                client.post("http://10.0.2.2/C302_P08_SecuredCloudAddressBook/deleteContact.php", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            if (response.getBoolean("authorized"))
                                switch (response.getInt("status")) {
                                    case 1:
                                        Toast.makeText(ViewContactDetailsActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                        finish();
                                        break;

                                    default:
                                        Toast.makeText(ViewContactDetailsActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            else
                                Toast.makeText(ViewContactDetailsActivity.this, response.getString("reason"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) { e.printStackTrace(); }
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Code for step 1 start
        Intent intent = getIntent();
        contactId = intent.getIntExtra("contact_id", -1);
        int loginId = intent.getIntExtra("loginId", 0);
        String apikey = intent.getStringExtra("apikey");

        RequestParams params = new RequestParams();
        params.add("id", String.valueOf(contactId));
        params.add("loginId", String.valueOf(loginId));
        params.add("apikey", apikey);

        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://10.0.2.2/C302_P08_SecuredCloudAddressBook/getContactDetails.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response != null) {
                        etFirstName.setText(response.getString("firstname"));
                        etLastName.setText(response.getString("lastname"));
                        etMobile.setText(response.getString("mobile"));
                    } else
                        Toast.makeText(ViewContactDetailsActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) { e.printStackTrace(); }
            }
        });
    }
}