package com.myapplicationdev.android.l12datamalltrafficincidents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.anychart.core.Chart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Incident> incidents;
    IncidentAdapter adapter;
    AsyncHttpClient client;
    FirebaseFirestore db;
    CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);
        incidents = new ArrayList<>();
        adapter = new IncidentAdapter(this, R.layout.incident_row, incidents);
        listView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("incidents");

        get_traffic();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("incidentSelected", incidents.get(position));
                startActivity(intent);
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
        if (id == R.id.google_map) {
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.upload_firebase) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("proceed to upload to firestore")
                    .setTitle("upload to firestore");

            builder.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        DocumentReference documentReference = collectionReference.document(document.getId());
                                        documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("MainActivity", "Successfully deleted from firebase");
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("MainActivity", "Failed to delete from into firebase");
                                            }
                                        });
                                    }
                                    for (Incident incident: incidents) {
                                        collectionReference.add(incident)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Log.d("MainActivity", "Successfully added into firebase");
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("MainActivity", "Failed to add into firebase");
                                            }
                                        });
                                    }
                                } else {
                                    Log.d("MainActivity", "Successfully added into firebase");
                                }
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (id == R.id.reload) {
            get_traffic();
            Toast.makeText(MainActivity.this, "Reloaded", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.chart) {
            Intent intent = new Intent(MainActivity.this, ChartActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void get_traffic() {
        client = new AsyncHttpClient();
        client.addHeader("AccountKey","We/4SNhISV+moxrLY/BVrw==");
        client.addHeader("accept","application/json");
        client.get("http://datamall2.mytransport.sg/ltaodataservice/TrafficIncidents", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                JSONObject incidentObj = null;
                incidents.clear();
                try {
                    JSONArray listIncidents = response.getJSONArray("value");
                    Log.i("listIncidents",listIncidents.toString());
                    for (int i = 0; i < listIncidents.length(); i++) {
                        incidentObj = (JSONObject) listIncidents.get(i);
                        String type = incidentObj.getString("Type");
                        double latitude = incidentObj.getDouble("Latitude");
                        double longitude = incidentObj.getDouble("Longitude");
                        String message = incidentObj.getString("Message");
                        DateFormat dateformat = new SimpleDateFormat("(dd/MM)HH:mm");
                        String dateString = message.split(" ")[0];
                        Date date = dateformat.parse(dateString);

                        Incident newIncident = new Incident(type, latitude, longitude, message, date);
                        incidents.add(newIncident);
                    }
                    adapter.notifyDataSetChanged();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}