package com.myapplicationdev.android.firebaseinventoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

public class AddActivity extends AppCompatActivity {

    EditText name, cost;
    Button add;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        db = FirebaseFirestore.getInstance();
        name = findViewById(R.id.add_name_edittext);
        cost = findViewById(R.id.add_cost_edittext);
        add = findViewById(R.id.add_btn);

        add.setOnClickListener(view -> {
            String name_text = name.getText().toString().trim();
            double cost_amount = Double.parseDouble(cost.getText().toString().trim());

            db.collection("inventory").document().set(new Phone(name_text, cost_amount));
            finish();
        });
    }
}