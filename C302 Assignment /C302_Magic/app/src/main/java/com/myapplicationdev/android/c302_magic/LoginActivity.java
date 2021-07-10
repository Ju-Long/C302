package com.myapplicationdev.android.c302_magic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText etLoginId, etPassword;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences("C302_Magic", Context.MODE_PRIVATE);
        btnLogin = findViewById(R.id.btnLogin);
        etLoginId = findViewById(R.id.etLoginId);
        etPassword = findViewById(R.id.etPassword);

        btnLogin.setOnClickListener(v -> {
            if (!(etLoginId.getText().toString().trim().isEmpty() && etPassword.getText().toString().trim().isEmpty())) {
                RequestParams params = new RequestParams();
                params.add("username", etLoginId.getText().toString().trim());
                params.add("password", etPassword.getText().toString().trim());

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                preferences = getSharedPreferences("C302_Magic", Context.MODE_PRIVATE);
                AsyncHttpClient client = new AsyncHttpClient();
                client.post("http://192.168.1.154/C302/C302_Magic/login.php", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            switch(response.getString("output")) {
                                case "success":
                                    JSONObject user_data = (JSONObject) response.get("result");
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putInt("user_id", user_data.getInt("id"));
                                    editor.putString("apikey", user_data.getString("apikey"));
                                    editor.putString("role", user_data.getString("role"));
                                    editor.commit();
                                    startActivity(intent);
                                    break;

                                default:
                                    Toast.makeText(LoginActivity.this, response.getString("output"), Toast.LENGTH_SHORT).show();
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
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}