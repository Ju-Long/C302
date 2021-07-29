package com.myapplicationdev.android.firebaseinventoryapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ArrayAdapterPhone extends ArrayAdapter<Phone> {

    Context context;
    ArrayList<Phone> phones;
    int resource;
    TextView name;
    ImageButton edit, delete;
    FirebaseFirestore db;
    ArrayList<String> document_ids;
    final String TAG = "ArrayAdapterPhone";

    public ArrayAdapterPhone(Context context, int resource, ArrayList<Phone> phones, ArrayList<String> document_ids) {
        super(context, resource, phones);
        this.context = context;
        this.phones = phones;
        this.document_ids = document_ids;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(resource, parent, false);
        db = FirebaseFirestore.getInstance();
        name = rowView.findViewById(R.id.product_name_textview);
        edit = rowView.findViewById(R.id.edit_row_button);
        delete = rowView.findViewById(R.id.delete_row_btn);

        Phone phone = phones.get(position);
        name.setText(phone.getName());

        edit.setOnClickListener(view -> {
            Intent intent = new Intent(context, AddEditActivity.class);
            intent.putExtra("type", "Edit");
            intent.putExtra("doc_id", document_ids.get(position));
            context.startActivity(intent);
        });

        delete.setOnClickListener(view -> {
            db.collection("inventory").document(document_ids.get(position)).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context.getApplicationContext(), "record deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context.getApplicationContext(), "record delete failed", Toast.LENGTH_SHORT).show();
                    }
                });
        });

        return rowView;
    }
}
