package com.myapplicationdev.android.firebasestudentapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView lvStudent;
    private ArrayList<Student> alStudent;
    private ArrayAdapter<Student> aaStudent;

    // TODO: Task 1 - Declare Firebase variables
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvStudent = (ListView)findViewById(R.id.listViewStudents);

        // TODO: Task 2: Get FirebaseFirestore instance and reference
        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("student");

        alStudent = new ArrayList<Student>();
        aaStudent = new ArrayAdapter<Student>(getApplicationContext(), android.R.layout.simple_list_item_1, alStudent);
        lvStudent.setAdapter(aaStudent);

        //TODO: Task 3: Get real time updates from firestore by listening to collection "students"
        collectionReference
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }
                        //TODO: Task 4: Read from Snapshot and add into ArrayAdapter for ListView
                        alStudent.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            alStudent.add(doc.toObject(Student.class));
                        }
                        aaStudent.notifyDataSetChanged();
                    }
                });

        lvStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Student student = alStudent.get(i);  // Get the selected Student
                Intent intent = new Intent(MainActivity.this, StudentDetailsActivity.class);
                intent.putExtra("StudentID", student.getId());
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.addStudent) {

            Intent intent = new Intent(getApplicationContext(), AddStudentActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
