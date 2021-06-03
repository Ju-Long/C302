package com.myapplicationdev.android.c302_p07_addressbook;

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

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ViewContactDetailsActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etMobile;
    private Button btnUpdate, btnDelete;
    private int contactId;
    private AsyncHttpClient client;

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
        contactId = intent.getIntExtra("contact_id", -1);

        //TODO: call getContactDetails.php with the id as a parameter
        //TODO: set the text fields with the data retrieved
        RequestParams params = new RequestParams("id", contactId);
        client.get("http://10.0.2.2/C302_P07/getItemById", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    etFirstName.setText(response.getString("firstname"));
                    etLastName.setText(response.getString("lastname"));
                    etMobile.setText(response.getString("mobile"));
                } catch (JSONException e) { e.printStackTrace(); }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUpdateOnClick(v);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDeleteOnClick(v);
            }
        });
    }//end onCreate

    
    private void btnUpdateOnClick(View v) {
		//TODO: retrieve the updated text fields and set as parameters to be passed to updateContact.php
        RequestParams params = new RequestParams();
        params.put("id", contactId);
        params.put("firstname", etFirstName.getText().toString());
        params.put("lastname", etLastName.getText().toString());
        params.put("mobile", etMobile.getText().toString());
        client.post("http://10.0.2.2/C302_P07/updateContact.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getString("message").equals("Record is updated successfully.")) {
                        Toast.makeText(getApplicationContext(), "Contact details successfully updated", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Contact details failed to updated", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (JSONException e) { e.printStackTrace(); }
            }
        });
    }//end btnUpdateOnClick

    private void btnDeleteOnClick(View v) {
		//TODO: retrieve the id and set as parameters to be passed to deleteContact.php
        RequestParams params = new RequestParams("id", contactId);
        client.post("http://10.0.2.2/C302_P07/deleteContact.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getInt("success") > 0) {
                        Toast.makeText(getApplicationContext(), "Contact details successfully deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Contact details failed deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (JSONException e) { e.printStackTrace(); }
            }
        });
        
    }//end btnDeleteOnClick

}//end class