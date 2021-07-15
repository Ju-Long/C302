package com.myapplicationdev.android.c302_p09_mcafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    SharedPreferences preferences;
    private EditText etLoginID, etPassword;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etLoginID = (EditText)findViewById(R.id.editTextLoginID);
        etPassword = (EditText)findViewById(R.id.editTextPassword);
        btnSubmit = (Button)findViewById(R.id.buttonSubmit);
        preferences = getSharedPreferences("C302_P09", Context.MODE_PRIVATE);

        btnSubmit.setOnClickListener(v -> {
            String username = etLoginID.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.equalsIgnoreCase("")) {
                Toast.makeText(LoginActivity.this, "Login failed. Please enter username.", Toast.LENGTH_LONG).show();

            } else if (password.equalsIgnoreCase("")) {
                Toast.makeText(LoginActivity.this, "Login failed. Please enter password.", Toast.LENGTH_LONG).show();

            } else {
                //proceed to authenticate user
                OnLogin(v);
            }
        });
    }

    private void OnLogin(View v) {
        // Point X - TODO: call doLogin web service to authenticate user
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("username", etLoginID.getText().toString().trim());
        params.add("password", etPassword.getText().toString().trim());

        client.post("http://10.0.2.2/C302_P09_mCafe/doLogin.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getBoolean("authenticated")) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("loginId", String.valueOf(response.getInt("id")));
                        editor.putString("apikey", response.getString("apikey"));
                        editor.putString("role", response.getString("role"));
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) { e.printStackTrace(); }
            }
        });
    }
}