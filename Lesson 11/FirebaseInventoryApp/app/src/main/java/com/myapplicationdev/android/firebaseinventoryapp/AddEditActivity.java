package com.myapplicationdev.android.firebaseinventoryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AddEditActivity extends AppCompatActivity {

    EditText name, cost;
    LinearLayout checkbox_container;
    Button save;
    TextView title;
    FirebaseFirestore db;
    final String TAG = "AddEditActivity";
    ArrayList<CheckBox> checkBoxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        db = FirebaseFirestore.getInstance();
        name = findViewById(R.id.add_edit_name_edittext);
        cost = findViewById(R.id.add_edit_cost_edittext);
        save = findViewById(R.id.save_btn);
        title = findViewById(R.id.title_textview);
        checkbox_container = findViewById(R.id.checkbox_container);
        checkBoxes = new ArrayList<>();

        title.setText(getIntent().getStringExtra("type") + " Item");

        db.collection("options")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            CheckBox checkBox = new CheckBox(getApplicationContext());
                            checkBox.setText(document.get("name", String.class));
                            checkbox_container.addView(checkBox);
                            checkBoxes.add(checkBox);
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });

        if (getIntent().getStringExtra("type").equals("Edit")) {
            String doc_id = getIntent().getStringExtra("doc_id");
            Log.d(TAG, "document id: " + doc_id);

            db.collection("inventory").document(doc_id)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        if (doc.exists()) {
                            Phone phone = doc.toObject(Phone.class);
                            name.setText(phone.getName());
                            cost.setText(String.valueOf(phone.getCost()));
                            if (phone.getOptions() != null) {
                                for (CheckBox checkbox: checkBoxes) {
                                    if (phone.getOptions().contains(checkbox.getText().toString()))
                                        checkbox.setChecked(true);
                                }
                            }
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }

                }
            });
        }

        save.setOnClickListener(view -> {
            ArrayList<String> options = new ArrayList<>();
            for (CheckBox checkbox : checkBoxes) {
                if (checkbox.isChecked())
                    options.add(checkbox.getText().toString());
            }

            String name_text = name.getText().toString().trim();
            double cost_amount = Double.parseDouble(cost.getText().toString().trim());
            Phone phone;
            if (options.isEmpty())
                phone = new Phone(name_text, cost_amount);
            else
                phone = new Phone(name_text, cost_amount, options);

            switch (getIntent().getStringExtra("type")) {
                case "Add":
                    db.collection("inventory").document().set(phone);
                    Toast.makeText(getApplicationContext(), "Product added", Toast.LENGTH_SHORT).show();
                    break;

                case "Edit":
                    db.collection("inventory").document(getIntent().getStringExtra("doc_id")).set(phone);
                    Toast.makeText(getApplicationContext(), "Product updated", Toast.LENGTH_SHORT).show();
                    break;
            }

            finish();
        });
    }
}