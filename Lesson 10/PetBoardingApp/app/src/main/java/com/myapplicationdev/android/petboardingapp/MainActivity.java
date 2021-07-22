package com.myapplicationdev.android.petboardingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button send;
    EditText name, no_of_days, boarding_date;
    Spinner pet_type;
    CheckBox is_vacc;
    Date selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        send = findViewById(R.id.send_btn);
        name = findViewById(R.id.name_edittext);
        no_of_days = findViewById(R.id.no_of_days_edittext);
        pet_type = findViewById(R.id.pet_type_spinner);
        boarding_date = findViewById(R.id.boarding_date_edittext);
        is_vacc = findViewById(R.id.is_vacc_checkbox);

        String[] pet_types = new String[]{"cat", "dog", "cow"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pet_types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pet_type.setAdapter(adapter);

        SingleDateAndTimePickerDialog.Builder dialog = new SingleDateAndTimePickerDialog.Builder(this)
                .bottomSheet()
                .curved()
                .displayMinutes(true)
                .displayHours(true)
                .displayDays(false)
                .displayMonth(true)
                .displayYears(true)
                .displayDaysOfMonth(true)
                .title("Boarding Date")
                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {
                        selectedDate = date;
                        boarding_date.setText(selectedDate.toString());
                    }
                });

        boarding_date.setOnClickListener(v -> {
            dialog.display();
        });

        send.setOnClickListener(v -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference collectionReference = db.collection("petboardings");
            DocumentReference documentReference = collectionReference.document("petboarding");

            Pet pet = new Pet(name.getText().toString(), Integer.parseInt(no_of_days.getText().toString()), pet_types[pet_type.getSelectedItemPosition()], selectedDate, is_vacc.isChecked());
            documentReference.set(pet);
        });
    }
}