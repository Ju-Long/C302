package com.myapplicationdev.android.firebasestudentapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddStudentActivity extends AppCompatActivity {

    private static final String TAG = "AddStudentActivity";

    private EditText etName, etAge;
    private Button btnAdd;

    // TODO: Task 1 - Declare Firebase variables
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        etName = (EditText) findViewById(R.id.editTextName);
        etAge = (EditText) findViewById(R.id.editTextAge);
        btnAdd = (Button) findViewById(R.id.buttonAdd);


        // TODO: Task 2: Get FirebaseFirestore instance and collection reference to "students"
        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("student");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: Task 3: Retrieve name and age from EditText and instantiate a new Student object
                String name = etName.getText().toString().trim();
                int age = Integer.parseInt(etAge.getText().toString().trim());
                Student student = new Student(name, age);

                //TODO: Task 4: Add student to database and go back to main screen
                collectionReference.document().set(student);
                finish();

            }
        });


    }
}
