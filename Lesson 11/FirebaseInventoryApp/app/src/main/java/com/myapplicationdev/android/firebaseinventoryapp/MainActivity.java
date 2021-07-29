package com.myapplicationdev.android.firebaseinventoryapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    final String TAG = "MainActivity";
    ListView list_of_product;
    ArrayList<Phone> products;
    ArrayList<String> document_ids;
//    ArrayAdapter<Phone> adapter;
    ArrayAdapterPhone adapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        list_of_product = findViewById(R.id.list_of_product);
        products = new ArrayList<>();
        document_ids = new ArrayList<>();
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, products);
        adapter = new ArrayAdapterPhone(this, R.layout.row, products, document_ids);
        list_of_product.setAdapter(adapter);

        db.collection("inventory")
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }

                    products.clear();
                    for (QueryDocumentSnapshot doc : value) {
                        document_ids.add(doc.getId());
                        products.add(doc.toObject(Phone.class));
                    }
                    adapter.notifyDataSetChanged();
                }
            });

//        list_of_product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(MainActivity.this, EditDeleteActivity.class);
//                intent.putExtra("product", products.get(i));
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == R.id.add_item) {
//            Intent intent = new Intent(getApplicationContext(), AddActivity.class);
            Intent intent = new Intent(getApplicationContext(), AddEditActivity.class);
            intent.putExtra("type", "Add");
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}