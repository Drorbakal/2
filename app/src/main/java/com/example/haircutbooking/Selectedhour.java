package com.example.haircutbooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haircutbooking.Data;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Selectedhour extends AppCompatActivity implements HourAdapter.OnHourClickListener {

    private String selectedHour = "";
    private List<String> barberList = new ArrayList<>();

    private DatabaseReference drorbookings;
    private DatabaseReference maybookings;
    private DatabaseReference ofekbookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_hour);
        String selectedDate = getIntent().getStringExtra("selectedDate");
        String today = getIntent().getStringExtra("today");
        String myhour = getIntent().getStringExtra("myhour");
        String isEnglish = getIntent().getStringExtra("isenglish");
        String isAdminFlag = getIntent().getStringExtra("isAdminFlag");
        boolean isFriday = getIntent().getBooleanExtra("isFriday", false);
        String account = getIntent().getStringExtra("account");
        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        String selectedBarber = getIntent().getStringExtra("selectedBarber");
        String selectedType = getIntent().getStringExtra("selectedType");
        Button bookbtn  = findViewById(R.id.bookingbtn);
        bookbtn.setClickable(false);
        bookbtn.setBackgroundResource(R.drawable.onclickable);
        Button alldaybreak = findViewById(R.id.alldaybreak);
        alldaybreak.setVisibility(View.GONE);
        drorbookings = FirebaseDatabase.getInstance().getReference("customers").
                child("Dror Bakal");
        maybookings = FirebaseDatabase.getInstance().getReference("customers").
                child("May Trabelsi");
        ofekbookings = FirebaseDatabase.getInstance().getReference("customers").
                child("Ofek  Daida");
        if (selectedBarber.equals("Dror Bakal")){
            if (account.equals("drorbakal17@gmail.com")){
                alldaybreak.setVisibility(View.VISIBLE);
                alldaybreak.setBackgroundResource(R.drawable.clickable);
            }
        }
        if (selectedBarber.equals("May Trabelsi")){
            if (account.equals("maytrabelsi1111@gmail.com")){
                alldaybreak.setVisibility(View.VISIBLE);
                alldaybreak.setBackgroundResource(R.drawable.clickable);
            }
        }
        if (selectedBarber.equals("Ofek Daida")){
            if (account.equals("ofekdaida16@gmail.com")){
                alldaybreak.setVisibility(View.VISIBLE);
                alldaybreak.setBackgroundResource(R.drawable.clickable);
            }
        }
        readfunc(new BarberDataCallback() {
            @Override
            public void onDataLoaded(List<String> barberList) {
                // Here, you can access the fetched data and use it as needed
                // For example, display it in a Toast
                StringBuilder builder = new StringBuilder();
                for (String booking : barberList) {
                    builder.append(booking).append("\n");
                }
                RecyclerView recyclerView = findViewById(R.id.recyclerViewHours);
                HourAdapter adapter = new HourAdapter(barberList, isFriday, selectedBarber, selectedDate, today, myhour, Selectedhour.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(Selectedhour.this));
                recyclerView.setAdapter(adapter);
            }
        });
        if(isEnglish.equals("no")){
            bookbtn.setText("קבע");
            alldaybreak.setText("יום חופש");
        }
    }

    @Override
    public void onHourClick(String hour) {
        Button bookbtn  = findViewById(R.id.bookingbtn);
        bookbtn.setClickable(true);
        bookbtn.setBackgroundResource(R.drawable.clickable);
        selectedHour = hour; // Store the selected hour
        RecyclerView recyclerViewHours = findViewById(R.id.recyclerViewHours);
        for (int i = 0; i < recyclerViewHours.getChildCount(); i++) {
            View childView = recyclerViewHours.getChildAt(i);
            Button button = childView.findViewById(R.id.buttonHour);
            if (button.getText().toString().equals(selectedHour)) {
                button.setBackgroundResource(R.drawable.clickable); // Set background for selected hour
            }
            else {
                button.setVisibility(View.GONE); // Hide the button for other hours
            }

        }
    }

    public void addfunc(View view) {
        String isAdminFlag = getIntent().getStringExtra("isAdminFlag");
        boolean isFriday = getIntent().getBooleanExtra("isFriday", false);
        String isEnglish = getIntent().getStringExtra("isenglish");
        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        String selectedDate = getIntent().getStringExtra("selectedDate");
        String date_and_time = selectedDate + ": " + selectedHour;
        String selectedType = getIntent().getStringExtra("selectedType");
        String selectedBarber = getIntent().getStringExtra("selectedBarber");
        String account = getIntent().getStringExtra("account");
        String lowerCaseEmail = account.toLowerCase();

        // Create a Data object with the input values
        Data data = new Data(name, phone, date_and_time, selectedType, selectedBarber, account);

        // Get the reference to the Firebase database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("customers").child(selectedBarber).push(); // Generate a unique key
        myRef.setValue(data, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    // If there was an error, display a toast message
                    Toast.makeText(Selectedhour.this, "Data could not be saved. Please try again.", Toast.LENGTH_SHORT).show();
                } else {
                    // If data was saved successfully, start MainActivity
                    Intent intent = new Intent(Selectedhour.this, MainActivity.class);
                    intent.putExtra("isAdminFlag", isAdminFlag);
                    intent.putExtra("account", account);
                    intent.putExtra("isenglish", isEnglish);
                    intent.putExtra("isFriday", isFriday);
                    intent.putExtra("selectedBarber" , selectedBarber);
                    deleteOutdatedBookingsForEdit(drorbookings,lowerCaseEmail);
                    deleteOutdatedBookingsForEdit(maybookings,lowerCaseEmail);
                    deleteOutdatedBookingsForEdit(ofekbookings,lowerCaseEmail);

                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void addfunc2(View view) {
        List<String> hours = new ArrayList<>();
        for (int i = 10; i < 21; i++) {
            for (int minute = 0; minute < 60; minute += 15) {
                hours.add(String.format("%02d:%02d", i, minute));
            }
        }
        DatabaseReference myRef = null;
        Data data = null;
        for (int i = 0; i < hours.size(); i++) {
            String isAdminFlag = getIntent().getStringExtra("isAdminFlag");
            boolean isFriday = getIntent().getBooleanExtra("isFriday", false);
            String isEnglish = getIntent().getStringExtra("isenglish");
            String name = getIntent().getStringExtra("name");
            String phone = getIntent().getStringExtra("phone");
            String selectedDate = getIntent().getStringExtra("selectedDate");
            String date_and_time = selectedDate + ": " + hours.get(i);
            String selectedType = getIntent().getStringExtra("selectedType");
            String selectedBarber = getIntent().getStringExtra("selectedBarber");
            String account = getIntent().getStringExtra("account");
            String lowerCaseEmail = account.toLowerCase();

            data = new Data(name, phone, date_and_time, selectedType, selectedBarber, account);

            // Get the reference to the Firebase database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            myRef = database.getReference("customers").child(selectedBarber).push();

            // Add the data to the Firebase database
            myRef.setValue(data, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        // If there was an error, display a toast message
                        Toast.makeText(Selectedhour.this, "Data could not be saved. Please try again.", Toast.LENGTH_SHORT).show();
                    } else {

                    }
                }
            });
        }
        String isAdminFlag = getIntent().getStringExtra("isAdminFlag");
        boolean isFriday = getIntent().getBooleanExtra("isFriday", false);
        String isEnglish = getIntent().getStringExtra("isenglish");
        String selectedBarber = getIntent().getStringExtra("selectedBarber");
        String account = getIntent().getStringExtra("account");
        String lowerCaseEmail = account.toLowerCase();
        Intent intent = new Intent(Selectedhour.this, MainActivity.class);
        intent.putExtra("isAdminFlag", isAdminFlag);
        intent.putExtra("account", account);
        intent.putExtra("isenglish", isEnglish);
        intent.putExtra("isFriday", isFriday);
        intent.putExtra("selectedBarber", selectedBarber);
        deleteOutdatedBookingsForEdit(drorbookings,lowerCaseEmail);
        deleteOutdatedBookingsForEdit(maybookings,lowerCaseEmail);
        deleteOutdatedBookingsForEdit(ofekbookings,lowerCaseEmail);
        startActivity(intent);
        finish();
    }

    public void readfunc(BarberDataCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String selectedBarber = getIntent().getStringExtra("selectedBarber");
        DatabaseReference selectedBarberRef = database.getReference("customers").child(selectedBarber);

        selectedBarberRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> selectedBarberList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Data data = snapshot.getValue(Data.class);
                    if (data != null) {
                        String dateAndTime = data.getDate_and_time();
                        String c_name = data.getName();
                        String c_phone = data.getPhone();
                        String c_email = data.getEmail();
                        selectedBarberList.add(dateAndTime);
                        selectedBarberList.add(c_phone);
                        selectedBarberList.add(c_name);
                        selectedBarberList.add(c_email);
                    }
                }
                // Invoke the callback with the fetched data
                callback.onDataLoaded(selectedBarberList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(Selectedhour.this, "Failed to read data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface BarberDataCallback {
        void onDataLoaded(List<String> barberList);
    }
    private void deleteOutdatedBookingsForEdit(DatabaseReference dbRef, final String email) {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Data data = dataSnapshot.getValue(Data.class);
                        if (data != null) {
                            if (data.getEmail().equals(email)) {
                                if (data.getName().startsWith("1")) {
                                    dataSnapshot.getRef().removeValue();
                                }
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled
            }
        });
    }
}
