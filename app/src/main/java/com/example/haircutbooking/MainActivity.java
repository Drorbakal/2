package com.example.haircutbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView appnamemain;
    String todayDateString; // String to hold today's date

    private Button ap_count;
    private DatabaseReference drorbookings;
    private DatabaseReference maybookings;
    private DatabaseReference ofekbookings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //delete();

        // Get data from intent
        String isEnglish = getIntent().getStringExtra("isenglish");
        String isAdminFlag = getIntent().getStringExtra("isAdminFlag");
        String account = getIntent().getStringExtra("account");
        Button message = findViewById(R.id.message);
        String lowerCaseEmail = account.toLowerCase();
        drorbookings = FirebaseDatabase.getInstance().getReference("customers").
                child("Dror Bakal");
        maybookings = FirebaseDatabase.getInstance().getReference("customers").
                child("May Trabelsi");
        ofekbookings = FirebaseDatabase.getInstance().getReference("customers").
                child("Ofek  Daida");
        appnamemain = findViewById(R.id.appnamemain);
        if (isEnglish.equals("no")) {
            appnamemain.setText("קביעת תספורת");
            message.setText("התורים שלי");
        }
        deleteOutdatedBookings(maybookings);
        deleteOutdatedBookings(drorbookings);
        deleteOutdatedBookings(ofekbookings);

        // Show toast message for admin login
        if (isAdminFlag.equals("yes")) {
            if (isEnglish.equals("no")) {
                Toast.makeText(MainActivity.this, "מנהל התחבר", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Admin Log In", Toast.LENGTH_SHORT).show();
            }
        }

        // Initialize CalendarView
        CalendarView calendarView = findViewById(R.id.calendar);

        // Set minimum date to today and maximum date to two weeks from today
        Calendar calendar = Calendar.getInstance();
        long todayInMillis = calendar.getTimeInMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        todayDateString = dateFormat.format(calendar.getTime());
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY) + 3; // Get the current hour in 24-hour format
        int currentMinute = calendar.get(Calendar.MINUTE); // Get the current minute
        String currentTime = String.format("%02d:%02d", currentHour, currentMinute);
        calendar.add(Calendar.DAY_OF_MONTH, 14);
        long twoWeeksInMillis = calendar.getTimeInMillis();
        calendarView.setMinDate(todayInMillis);
        calendarView.setMaxDate(twoWeeksInMillis);

        message.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (lowerCaseEmail.equals("maytrabelsi1111@gmail.com")) {
                    maybookings.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                StringBuilder allDataBuilder = new StringBuilder();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    Data data = dataSnapshot.getValue(Data.class);
                                    if (data != null) {
                                        String name = data.getName();
                                        String phone = data.getPhone();
                                        String dateAndTime = data.getDate_and_time();
                                        if (!name.equals("-")) {
                                            allDataBuilder.append("Name: ").append(name).append("\n")
                                                    .append("Phone: ").append(phone).append("\n")
                                                    .append("Date and Time: ").append(dateAndTime).append("\n\n");
                                        }
                                    } else {
                                        Toast.makeText(MainActivity.this, "Invalid data format", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                // Create the dialog
                                View dialogView = getLayoutInflater().inflate(R.layout.message, null);
                                TextView textView = dialogView.findViewById(R.id.textView);
                                if (allDataBuilder.length() > 0) {
                                    textView.setText(allDataBuilder.toString());
                                } else {
                                    textView.setText("No booking for today or later");
                                }

                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setView(dialogView);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Close the dialog
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            } else {
                                Toast.makeText(MainActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle onCancelled
                        }
                    });
                }
                if (lowerCaseEmail.equals("drorbakal17@gmail.com")) {
                    drorbookings.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                StringBuilder allDataBuilder = new StringBuilder();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    Data data = dataSnapshot.getValue(Data.class);
                                    if (data != null) {
                                        String name = data.getName();
                                        String phone = data.getPhone();
                                        String dateAndTime = data.getDate_and_time();
                                        if (!name.equals("-")) {
                                            allDataBuilder.append("Name: ").append(name).append("\n")
                                                    .append("Phone: ").append(phone).append("\n")
                                                    .append("Date and Time: ").append(dateAndTime).append("\n\n");
                                        }
                                    } else {
                                        Toast.makeText(MainActivity.this, "Invalid data format", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                // Create the dialog
                                View dialogView = getLayoutInflater().inflate(R.layout.message, null);
                                TextView textView = dialogView.findViewById(R.id.textView);
                                if (allDataBuilder.length() > 0) {
                                    textView.setText(allDataBuilder.toString());
                                } else {
                                    textView.setText("No booking for today or later");
                                }

                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setView(dialogView);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Close the dialog
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            } else {
                                Toast.makeText(MainActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle onCancelled
                        }
                    });
                }
                if (lowerCaseEmail.equals("ofekdaida16@gmail.com")) {
                    ofekbookings.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                StringBuilder allDataBuilder = new StringBuilder();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    Data data = dataSnapshot.getValue(Data.class);
                                    if (data != null) {
                                        String name = data.getName();
                                        String phone = data.getPhone();
                                        String dateAndTime = data.getDate_and_time();
                                        if (!name.equals("-")) {
                                            allDataBuilder.append("Name: ").append(name).append("\n")
                                                    .append("Phone: ").append(phone).append("\n")
                                                    .append("Date and Time: ").append(dateAndTime).append("\n\n");
                                        }
                                    } else {
                                        Toast.makeText(MainActivity.this, "Invalid data format", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                // Create the dialog
                                View dialogView = getLayoutInflater().inflate(R.layout.message, null);
                                TextView textView = dialogView.findViewById(R.id.textView);
                                if (allDataBuilder.length() > 0) {
                                    textView.setText(allDataBuilder.toString());
                                }
                                else {
                                    textView.setText("No booking for today or later");
                                }

                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setView(dialogView);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Close the dialog
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            } else {
                                Toast.makeText(MainActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle onCancelled
                        }
                    });
                }
                drorbookings.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String droraccount = "drorbakal17@gmail.com";
                        if (snapshot.exists()) {
                            StringBuilder allDataBuilder = new StringBuilder();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Data data = dataSnapshot.getValue(Data.class);
                                if (data != null) {
                                    String mail = data.getEmail();
                                    String lowerCaseEmail2 = mail.toLowerCase();
                                    if (mail.equals(account)&& !mail.equals(droraccount)){
                                        String name = data.getName();
                                        String phone = data.getPhone();
                                        String dateAndTime = data.getDate_and_time();
                                        if (!name.equals("-")) {
                                            allDataBuilder.append("Name: ").append(name).append("\n")
                                                    .append("Phone: ").append(phone).append("\n")
                                                    .append("Barber: ").append("Dror Bakal").append("\n")
                                                    .append("Date and Time: ").append(dateAndTime).append("\n\n");
                                        }
                                    }
                                } else {
                                }
                            }

                            // Create the dialog
                            View dialogView = getLayoutInflater().inflate(R.layout.message_for_customers, null);
                            TextView textView = dialogView.findViewById(R.id.textView2);
                            if (allDataBuilder.length() > 0) {
                                textView.setText(allDataBuilder.toString());
                            }
                            else {
                                textView.setText("No booking for today or later");
                            }

                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setView(dialogView);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Close the dialog
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle onCancelled
                    }
                });
                maybookings.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String mayaccount = "maytrabelsi1111@gmail.com";
                        if (snapshot.exists()) {
                            StringBuilder allDataBuilder = new StringBuilder();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Data data = dataSnapshot.getValue(Data.class);
                                if (data != null) {
                                    String mail = data.getEmail();
                                    String lowerCaseEmail2 = mail.toLowerCase();
                                    if (mail.equals(account) && !mail.equals(mayaccount)){
                                        String name = data.getName();
                                        String phone = data.getPhone();
                                        String dateAndTime = data.getDate_and_time();
                                        if (!name.equals("-")) {
                                            allDataBuilder.append("Name: ").append(name).append("\n")
                                                    .append("Phone: ").append(phone).append("\n")
                                                    .append("Barber: ").append("May Trabelsi").append("\n")
                                                    .append("Date and Time: ").append(dateAndTime).append("\n\n");
                                        }
                                    }
                                } else {
                                }
                            }

                            // Create the dialog
                            View dialogView = getLayoutInflater().inflate(R.layout.message_for_customers, null);
                            TextView textView = dialogView.findViewById(R.id.textView2);
                            if (allDataBuilder.length() > 0) {
                                textView.setText(allDataBuilder.toString());
                            } else {
                                textView.setText("No booking for today or later");
                            }

                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setView(dialogView);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Close the dialog
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle onCancelled
                    }
                });
                ofekbookings.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String ofekaccount = "ofekdaida16@gmail.com";
                        if (snapshot.exists()) {
                            StringBuilder allDataBuilder = new StringBuilder();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Data data = dataSnapshot.getValue(Data.class);
                                if (data != null) {
                                    String mail = data.getEmail();
                                    String lowerCaseEmail2 = mail.toLowerCase();
                                    if (mail.equals(account) && !mail.equals(ofekaccount)){
                                        String name = data.getName();
                                        String phone = data.getPhone();
                                        String dateAndTime = data.getDate_and_time();
                                        if (!name.equals("-")) {
                                            allDataBuilder.append("Name: ").append(name).append("\n")
                                                    .append("Phone: ").append(phone).append("\n")
                                                    .append("Barber: ").append("Ofek Daida").append("\n")
                                                    .append("Date and Time: ").append(dateAndTime).append("\n\n");
                                        }
                                    }
                                } else {
                                }
                            }

                            // Create the dialog
                            View dialogView = getLayoutInflater().inflate(R.layout.message_for_customers, null);
                            TextView textView = dialogView.findViewById(R.id.textView2);
                            if (allDataBuilder.length() > 0) {
                                textView.setText(allDataBuilder.toString());
                            } else {
                                textView.setText("No booking for today or later");
                            }

                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setView(dialogView);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Close the dialog
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle onCancelled
                    }
                });
            }
        });


        // Set listener for date selection
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Create a Calendar instance for the selected date
                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.set(year, month, dayOfMonth);

                // Check if the selected day is Saturday
                if (selectedCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                    // Show a toast indicating that Saturdays are not available
                    Toast.makeText(MainActivity.this, "Saturdays are not available for booking", Toast.LENGTH_SHORT).show();
                    if (isEnglish.equals("no")) {
                        Toast.makeText(MainActivity.this, "ימי שבת לא זמינים לתספורות", Toast.LENGTH_SHORT).show();
                    }
                    return; // Exit the method
                }

                // Check if the selected day is every seventh day from the initial blocked date
                Calendar initialBlockedDate = Calendar.getInstance();
                initialBlockedDate.set(2024, Calendar.APRIL, 6); // Set the initial blocked date to 6/4/24

                long millisSinceInitialBlockedDate = selectedCalendar.getTimeInMillis() - initialBlockedDate.getTimeInMillis();
                long daysSinceInitialBlockedDate = millisSinceInitialBlockedDate / (1000 * 60 * 60 * 24);

                if (daysSinceInitialBlockedDate % 7 == 0) {
                    // Show a toast indicating that this day is unavailable
                    Toast.makeText(MainActivity.this, "This day is unavailable for booking", Toast.LENGTH_SHORT).show();
                    if (isEnglish.equals("no")) {
                        Toast.makeText(MainActivity.this, "היום הזה לא זמין לתספורת", Toast.LENGTH_SHORT).show();
                    }
                    return; // Exit the method
                }
                boolean isFriday = selectedCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
                // If the selected date is not Saturday or every seventh day, proceed with booking
                Intent intent = new Intent(MainActivity.this, BookingPage.class);
                intent.putExtra("isAdminFlag", isAdminFlag);
                intent.putExtra("account", account);
                intent.putExtra("isenglish", isEnglish);
                String selectedDate = String.format("%02d/%02d/%d", dayOfMonth, (month + 1), year);
                intent.putExtra("selectedDate", selectedDate);
                intent.putExtra("todayDateString", todayDateString); // Pass today's date string to the next activity
                intent.putExtra("currentTime", currentTime);
                intent.putExtra("isFriday", isFriday);
                startActivity(intent);
            }
        });
    }

    private void deleteOutdatedBookingsForEmail(DatabaseReference dbRef, final String email) {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Data data = dataSnapshot.getValue(Data.class);
                        if (data != null) {
                            if (data.getEmail().equals(email)) {
                                dataSnapshot.getRef().removeValue();
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

    private void deleteOutdatedBookings(DatabaseReference dbRef) {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Calendar calendar = Calendar.getInstance();
                long todayInMillis = calendar.getTimeInMillis();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                todayDateString = dateFormat.format(calendar.getTime());
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Data data = dataSnapshot.getValue(Data.class);
                        if (data != null) {
                            String dateAndTime = data.getDate_and_time();
                            String[] todayparts = todayDateString.split("/");
                            String todayday = todayparts[0].trim();
                            String todaymonth = todayparts[1].trim();
                            String[] dateTimeParts = dateAndTime.split(": ");
                            String date = dateTimeParts[0].trim();
                            String time = dateTimeParts[1].trim();
                            String[]timeparts = time.split(":");
                            String hour = timeparts[0].trim();
                            String min = timeparts[1].trim();
                            int currentHour = calendar.get(Calendar.HOUR_OF_DAY) + 3; // Get the current hour in 24-hour format
                            int currentMinute = calendar.get(Calendar.MINUTE);
                            int hourInt = Integer.parseInt(hour);
                            int minInt = Integer.parseInt(min);
                            String[] dateparts = date.split("/");
                            String day = dateparts[0].trim();
                            String month = dateparts[1].trim();
                            int monthInt = Integer.parseInt(month);
                            int dayInt = Integer.parseInt(day);
                            int todaydayInt = Integer.parseInt(todayday);
                            int todaymonthInt = Integer.parseInt(todaymonth);
                            if (todaydayInt>dayInt){
                                if (todaymonthInt==monthInt) {
                                    dataSnapshot.getRef().removeValue();
                                }
                            }
                            else {
                                if (todaydayInt == dayInt) {
                                    if (hourInt < currentHour) {
                                        dataSnapshot.getRef().removeValue();
                                    }
                                    if (hourInt == currentHour) {
                                        if (minInt < currentMinute) {
                                            dataSnapshot.getRef().removeValue();
                                        }
                                    }
                                }
                            }
                        }
                        else {
                        }
                    }
                }
                else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled
            }
        });
    }

    private void updateDataForEmail(DatabaseReference dbRef, final String email) {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Data data = dataSnapshot.getValue(Data.class);
                        if (data != null) {
                            if (data.getEmail().equals(email)) {
                                String name = data.getName();
                                dataSnapshot.getRef().child("name").setValue(1+name);
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


    public void funcButton(View view){
        drorbookings = FirebaseDatabase.getInstance().getReference("customers").
                child("Dror Bakal");
        maybookings = FirebaseDatabase.getInstance().getReference("customers").
                child("May Trabelsi");
        ofekbookings = FirebaseDatabase.getInstance().getReference("customers").
                child("Ofek  Daida");
        int buttonId = view.getId();
        if (buttonId == R.id.cancel){
            String account = getIntent().getStringExtra("account");
            String lowerCaseEmail = account.toLowerCase();
            deleteOutdatedBookingsForEmail(drorbookings,lowerCaseEmail);
            deleteOutdatedBookingsForEmail(maybookings,lowerCaseEmail);
            deleteOutdatedBookingsForEmail(ofekbookings,lowerCaseEmail);
        }
        if(buttonId == R.id.edit_book){
            String isEnglish = getIntent().getStringExtra("isenglish");
            String isAdminFlag = getIntent().getStringExtra("isAdminFlag");
            String account = getIntent().getStringExtra("account");
            String lowerCaseEmail = account.toLowerCase();
            updateDataForEmail(drorbookings,lowerCaseEmail);
            updateDataForEmail(maybookings,lowerCaseEmail);
            updateDataForEmail(ofekbookings,lowerCaseEmail);
            Toast.makeText(MainActivity.this, "Book A New Date :", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.putExtra("isAdminFlag", isAdminFlag);
            intent.putExtra("account", account);
            intent.putExtra("isenglish", isEnglish);
            intent.putExtra("todayDateString", todayDateString);
            startActivity(intent);
        }
    }
}