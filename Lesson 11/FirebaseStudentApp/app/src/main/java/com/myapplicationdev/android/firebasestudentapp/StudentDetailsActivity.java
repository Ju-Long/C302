package com.myapplicationdev.android.firebasestudentapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class StudentDetailsActivity extends AppCompatActivity {

    private static final String TAG = "StudentDetailsActivity";

    private EditText etName, etAge;
    private Button btnUpdate, btnDelete;

    private Student student;

    // TODO: Task 1 - Declare Firebase variables
    FirebaseFirestore db;
    DocumentReference document;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        etName = (EditText) findViewById(R.id.editTextName);
        etAge = (EditText) findViewById(R.id.editTextAge);
        btnUpdate = (Button) findViewById(R.id.buttonUpdate);
        btnDelete = (Button) findViewById(R.id.buttonDelete);

        Intent intent = getIntent();
        String id = (String) intent.getStringExtra("StudentID");

        // TODO: Task 2: Get FirebaseFirestore instance
        db = FirebaseFirestore.getInstance();

        //TODO: Task 3: Get document reference by the student's id and set the name and age to EditText
        db.collection("student")
                .whereEqualTo("id", id)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        for (QueryDocumentSnapshot doc : value) {
                            document = db.collection("student").document(doc.getId());
                            student = doc.toObject(Student.class);
                            etName.setText(student.getName());
                            etAge.setText(student.getAge());
                        }
                    }
                });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Task 4: Update Student record based on input given
                String name = etName.getText().toString().trim();
                int age = Integer.parseInt(etAge.getText().toString().trim());

                document.set(new Student(name, age));
                Toast.makeText(getApplicationContext(), "Student record updated successfully", Toast.LENGTH_SHORT).show();

                finish();
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Task 5: Delete Student record based on student id
                document
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Student record deleted successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Student record delete failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                finish();
            }
        });


    }
}
