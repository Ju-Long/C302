package com.myapplicationdev.android.firebaseinventoryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class EditDeleteActivity extends AppCompatActivity {

    final String TAG = "EditDeleteActivity";
    EditText name, cost;
    Button update, delete;
    FirebaseFirestore db;
    DocumentReference document;
    Phone phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete);

        db = FirebaseFirestore.getInstance();
        name = findViewById(R.id.edit_name_edittext);
        cost = findViewById(R.id.edit_cost_edittext);
        update = findViewById(R.id.update_btn);
        delete = findViewById(R.id.delete_btn);
        phone = (Phone) getIntent().getSerializableExtra("phone");

        db.collection("inventory")
            .whereEqualTo("name", phone.getName())
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value,
                                    @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }

                    for (QueryDocumentSnapshot doc : value) {
                        document = db.collection("inventory").document(doc.getId());
                        phone = doc.toObject(Phone.class);
                        name.setText(phone.getName());
                        cost.setText(String.valueOf(phone.getCost()));
                    }
                }
            });

        update.setOnClickListener(view -> {
            String name_text = name.getText().toString().trim();
            double cost_amount = Double.parseDouble(cost.getText().toString().trim());

            document.set(new Phone(name_text, cost_amount));
            Toast.makeText(getApplicationContext(), "record update successfully", Toast.LENGTH_SHORT).show();
            finish();
        });

        delete.setOnClickListener(view -> {
            document
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "record deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Student record delete failed", Toast.LENGTH_SHORT).show();
                    }
                });

            finish();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == R.id.add_item) {
            Intent intent = new Intent(getApplicationContext(), AddActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}