package com.myapplicationdev.android.c302firebasehelloworld;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class MainActivity extends AppCompatActivity {

    private TextView tvMessage;
    private EditText etMessage;
    private TextView tvPriority;
    private EditText etPriority;
    private Button btnUpdate;

    private FirebaseFirestore db;
    private CollectionReference collectionReference;
    private DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMessage = findViewById(R.id.textViewMessage);
        etMessage = findViewById(R.id.editTextMessage);
        tvPriority = findViewById(R.id.textViewPriority);
        etPriority = findViewById(R.id.editTextPriority);

        btnUpdate = findViewById(R.id.buttonUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUpdateOnClick(v);
            }
        });

        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("messages");
        documentReference = collectionReference.document("message");

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    error.printStackTrace();
                    return;
                }

                if (value != null && value.exists()) {
                    Message msg = value.toObject(Message.class);
                    tvMessage.setText(msg.getText());
                    tvPriority.setText("Priority is " + msg.getPriority());
                }

            }
        });
    }

    private void btnUpdateOnClick(View v) {
        String text = etMessage.getText().toString();
        String priority = etPriority.getText().toString();
        Message msg = new Message(text, priority);

    }
}
