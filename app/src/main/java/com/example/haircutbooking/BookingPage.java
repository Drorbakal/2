package com.example.haircutbooking;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haircutbooking.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class BookingPage extends AppCompatActivity{

    private String selectedType = ""; // Variable to store the selected type (men or women)
    private String selectedBarber = ""; // Variable to store the selected barber
    private String selectedemail = "";
    TextInputEditText editname, editphone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookingpage);
        String isEnglish = getIntent().getStringExtra("isenglish");
        String isAdminFlag = getIntent().getStringExtra("isAdminFlag");
        boolean isFriday = getIntent().getBooleanExtra("isFriday", false);
        String selectedDate = getIntent().getStringExtra("selectedDate");
        String account = getIntent().getStringExtra("account");
        String today = getIntent().getStringExtra("todayDateString");
        String myhour = getIntent().getStringExtra("currentTime");
        selectedemail = account;
        ImageView maleImageView = findViewById(R.id.male);
        ImageView femaleImageView = findViewById(R.id.female);
        Button drorButton = findViewById(R.id.dror);
        Button mayButton = findViewById(R.id.may);
        Button ofekButton = findViewById(R.id.ofek);
        editname = findViewById(R.id.name);
        editphone = findViewById(R.id.phone);
        Button selectedbtn  = findViewById(R.id.selecthour);
        Button breakbtn = findViewById(R.id.breakbtn);
        Button choosehour = findViewById(R.id.selecthour);
        choosehour.setClickable(false);
        choosehour.setBackgroundResource(R.drawable.onclickable);
        breakbtn.setVisibility(View.GONE);
        if (isEnglish.equals("no")) {
            Toast.makeText(BookingPage.this, "תאריך נבחר: " + selectedDate, Toast.LENGTH_SHORT).show();
            drorButton.setText("דרור בקל");
            mayButton.setText("מאי טרבלסי");
            ofekButton.setText("אופק דאידה");
            editname.setHint("שם");
            editphone.setHint("טלפון");
            selectedbtn.setText("בחר שעה");
        }
        else {
            Toast.makeText(BookingPage.this, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
        }

        maleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedType.equals("Men")) {
                    // Deselect if already selected
                    selectedType = "";
                    maleImageView.setBackgroundResource(0); // Remove border
                    maleImageView.setClickable(true); // Make clickable
                    femaleImageView.setClickable(true); // Make clickable
                    mayButton.setBackgroundResource(R.drawable.onclickable); // Change mayButton background
                    ofekButton.setBackgroundResource(R.drawable.onclickable); // Change ofekButton background
                    mayButton.setClickable(true); // Make mayButton clickable
                    ofekButton.setClickable(true); // Make ofekButton clickable
                    String name = ((EditText) findViewById(R.id.name)).getText().toString();
                    String phone = ((EditText) findViewById(R.id.phone)).getText().toString();
                    Button choosehour = findViewById(R.id.selecthour);
                    choosehour.setClickable(false);
                    choosehour.setBackgroundResource(R.drawable.onclickable);
                }
                else {
                    // Select if not already selected
                    selectedType = "Men";
                    maleImageView.setBackgroundResource(R.drawable.border); // Apply border
                    maleImageView.setClickable(false); // Make unclickable
                    femaleImageView.setClickable(false); // Make unclickable
                    mayButton.setBackgroundResource(R.drawable.onclickable); // Apply border
                    ofekButton.setBackgroundResource(R.drawable.onclickable); // Apply border
                    mayButton.setClickable(false); // Make unclickable
                    ofekButton.setClickable(false); // Make unclickable
                    String name = ((EditText) findViewById(R.id.name)).getText().toString();
                    String phone = ((EditText) findViewById(R.id.phone)).getText().toString();
                    Button choosehour = findViewById(R.id.selecthour);
                    choosehour.setClickable(false);
                    choosehour.setBackgroundResource(R.drawable.onclickable);
                }
            }
        });
        femaleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedType.equals("Women")) {
                    selectedType = ""; // Deselect if already selected
                    femaleImageView.setBackgroundResource(0); // Remove border
                    femaleImageView.setClickable(true); // Make clickable
                    maleImageView.setClickable(true); // Make clickable
                    drorButton.setBackgroundResource(R.drawable.onclickable); // Change drorButton background
                    drorButton.setClickable(true); // Make drorButton clickable
                    String name = ((EditText) findViewById(R.id.name)).getText().toString();
                    String phone = ((EditText) findViewById(R.id.phone)).getText().toString();
                    Button choosehour = findViewById(R.id.selecthour);
                    choosehour.setClickable(false);
                    choosehour.setBackgroundResource(R.drawable.onclickable);
                }
                else {
                    selectedType = "Women"; // Set selected type to Women
                    femaleImageView.setBackgroundResource(R.drawable.border); // Apply border
                    femaleImageView.setClickable(false); // Make unclickable
                    maleImageView.setClickable(false); // Make unclickable
                    drorButton.setBackgroundResource(R.drawable.onclickable); // Change drorButton background
                    drorButton.setClickable(false); // Make drorButton unclickable
                    String name = ((EditText) findViewById(R.id.name)).getText().toString();
                    String phone = ((EditText) findViewById(R.id.phone)).getText().toString();
                    Button choosehour = findViewById(R.id.selecthour);
                    choosehour.setClickable(false);
                    choosehour.setBackgroundResource(R.drawable.onclickable);
                }
            }
        });

        drorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button breakbtn = findViewById(R.id.breakbtn);
                String account = getIntent().getStringExtra("account");
                if (account.equals("drorbakal17@gmail.com")){
                    breakbtn.setVisibility(View.VISIBLE);
                    if (isEnglish.equals("no")){
                        breakbtn.setText("קח חופשה");
                    }
                }
                selectedBarber = "Dror Bakal";
                selectedType = "Men";
                maleImageView.setBackgroundResource(R.drawable.border); // Apply border
                femaleImageView.setClickable(false); // Make unclickable
                maleImageView.setClickable(false);
                mayButton.setBackgroundResource(R.drawable.onclickable); // Change ofekButton background
                mayButton.setClickable(false); // Make ofekButton clickable
                ofekButton.setBackgroundResource(R.drawable.onclickable); // Change drorButton background
                ofekButton.setClickable(false);
                String name = ((EditText) findViewById(R.id.name)).getText().toString();
                String phone = ((EditText) findViewById(R.id.phone)).getText().toString();
                Button choosehour = findViewById(R.id.selecthour);
                if (name.isEmpty()) {
                    choosehour.setClickable(false);
                    choosehour.setBackgroundResource(R.drawable.onclickable);
                    if (isEnglish.equals("no")) {
                        Toast.makeText(BookingPage.this, "בבקשה, הזן את שמך", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BookingPage.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                    }
                    return; // Exit the method
                }
                if (phone.isEmpty()){
                    choosehour.setClickable(false);
                    choosehour.setBackgroundResource(R.drawable.onclickable);
                }
                if (!name.isEmpty() && !phone.isEmpty()){
                    if (!isValidPhoneNumber(phone)) {
                        if (isEnglish.equals("no")) {
                            Toast.makeText(BookingPage.this, "בבקשה, הזן מספר טלפון תקין", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(BookingPage.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                        }
                        return; // Exit the method
                    }
                    choosehour.setClickable(true);
                    choosehour.setBackgroundResource(R.drawable.clickable);
                }
            }
        });

        mayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button breakbtn = findViewById(R.id.breakbtn);
                String account = getIntent().getStringExtra("account");
                if (account.equals("maytrabelsi1111@gmail.com")){
                    breakbtn.setVisibility(View.VISIBLE);
                    if (isEnglish.equals("no")){
                        breakbtn.setText("קח חופשה");
                    }
                }
                selectedBarber = "May Trabelsi";
                selectedType = "Women";
                femaleImageView.setBackgroundResource(R.drawable.border); // Apply border
                femaleImageView.setClickable(false); // Make unclickable
                maleImageView.setClickable(false); // Make unclickable
                ofekButton.setBackgroundResource(R.drawable.onclickable); // Change ofekButton background
                ofekButton.setClickable(false); // Make ofekButton clickable
                drorButton.setBackgroundResource(R.drawable.onclickable); // Change drorButton background
                drorButton.setClickable(false);
                String name = ((EditText) findViewById(R.id.name)).getText().toString();
                String phone = ((EditText) findViewById(R.id.phone)).getText().toString();
                Button choosehour = findViewById(R.id.selecthour);
                if (name.isEmpty()) {
                    choosehour.setClickable(false);
                    choosehour.setBackgroundResource(R.drawable.onclickable);
                    if (isEnglish.equals("no")) {
                        Toast.makeText(BookingPage.this, "בבקשה, הזן את שמך", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BookingPage.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                    }
                    return; // Exit the method
                }
                if (phone.isEmpty()){
                    choosehour.setClickable(false);
                    choosehour.setBackgroundResource(R.drawable.onclickable);
                }
                if (!name.isEmpty() && !phone.isEmpty()){
                    if (!isValidPhoneNumber(phone)) {
                        if (isEnglish.equals("no")) {
                            Toast.makeText(BookingPage.this, "בבקשה, הזן מספר טלפון תקין", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(BookingPage.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                        }
                        return; // Exit the method
                    }
                    choosehour.setClickable(true);
                    choosehour.setBackgroundResource(R.drawable.clickable);
                }
            }
        });

        ofekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button breakbtn = findViewById(R.id.breakbtn);
                String account = getIntent().getStringExtra("account");
                if (account.equals("ofekdaida16@gmail.com")) {
                    breakbtn.setVisibility(View.VISIBLE);
                    if (isEnglish.equals("no")) {
                        breakbtn.setText("קח חופשה");
                    }
                }
                selectedBarber = "Ofek Daida";
                selectedType = "Women";
                femaleImageView.setBackgroundResource(R.drawable.border); // Apply border
                femaleImageView.setClickable(false); // Make unclickable
                maleImageView.setClickable(false);
                mayButton.setBackgroundResource(R.drawable.onclickable); // Change ofekButton background
                mayButton.setClickable(false); // Make ofekButton clickable
                drorButton.setBackgroundResource(R.drawable.onclickable); // Change drorButton background
                drorButton.setClickable(false);
                String name = ((EditText) findViewById(R.id.name)).getText().toString();
                String phone = ((EditText) findViewById(R.id.phone)).getText().toString();
                Button choosehour = findViewById(R.id.selecthour);
                if (name.isEmpty()) {
                    choosehour.setClickable(false);
                    choosehour.setBackgroundResource(R.drawable.onclickable);
                    if (isEnglish.equals("no")) {
                        Toast.makeText(BookingPage.this, "בבקשה, הזן את שמך", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BookingPage.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                    }
                    return; // Exit the method
                }
                if (phone.isEmpty()){
                    choosehour.setClickable(false);
                    choosehour.setBackgroundResource(R.drawable.onclickable);
                }
                if (!name.isEmpty() && !phone.isEmpty()){
                    if (!isValidPhoneNumber(phone)) {
                        choosehour.setClickable(false);
                        choosehour.setBackgroundResource(R.drawable.onclickable);
                        if (isEnglish.equals("no")) {
                            Toast.makeText(BookingPage.this, "בבקשה, הזן מספר טלפון תקין", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(BookingPage.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                        }
                        return; // Exit the method
                    }
                    choosehour.setClickable(true);
                    choosehour.setBackgroundResource(R.drawable.clickable);
                }
            }
        });
    }


    private boolean isValidPhoneNumber(String phoneNumber) {
        // Check if the phone number is exactly 10 characters long, starts with "05", and consists only of digits
        return phoneNumber.length() == 10 && phoneNumber.startsWith("05") && phoneNumber.matches("\\d+");
    }


    public void funcButton(View view) {
        String isEnglish = getIntent().getStringExtra("isenglish");
        String isAdminFlag = getIntent().getStringExtra("isAdminFlag");
        boolean isFriday = getIntent().getBooleanExtra("isFriday", false);
        String selectedDate = getIntent().getStringExtra("selectedDate");
        String account = getIntent().getStringExtra("account");
        String today = getIntent().getStringExtra("todayDateString");
        String myhour = getIntent().getStringExtra("currentTime");
        String name = ((EditText) findViewById(R.id.name)).getText().toString();
        String phone = ((EditText) findViewById(R.id.phone)).getText().toString();
        Button choosehour = findViewById(R.id.selecthour);
        if (name.isEmpty()){
            choosehour.setClickable(false);
            choosehour.setBackgroundResource(R.drawable.onclickable);
        }
        if (phone.isEmpty()){
            choosehour.setClickable(false);
            choosehour.setBackgroundResource(R.drawable.onclickable);
        }
        if (!name.isEmpty() && !phone.isEmpty()){
            choosehour.setClickable(true);
            choosehour.setBackgroundResource(R.drawable.clickable);
        }
        int buttonId = view.getId();
        if (buttonId == R.id.selecthour) {
            if (name.trim().isEmpty()) {
                if (isEnglish.equals("no")) {
                    Toast.makeText(BookingPage.this, "בבקשה, הזן את שמך", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BookingPage.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                }
                return; // Exit the method
            }

            if (!isValidPhoneNumber(phone)) {
                if (isEnglish.equals("no")) {
                    Toast.makeText(BookingPage.this, "בבקשה, הזן מספר טלפון תקין", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BookingPage.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                }
                return; // Exit the method
            }
            Intent intent = new Intent(BookingPage.this, Selectedhour.class);
            intent.putExtra("isenglish", getIntent().getStringExtra("isenglish"));
            intent.putExtra("isAdmin", getIntent().getStringExtra("isAdmin"));
            intent.putExtra("isFriday", getIntent().getBooleanExtra("isFriday", false));
            intent.putExtra("selectedDate", getIntent().getStringExtra("selectedDate"));
            intent.putExtra("today", getIntent().getStringExtra("todayDateString"));
            intent.putExtra("myhour", getIntent().getStringExtra("currentTime"));
            intent.putExtra("selectedBarber", selectedBarber);
            intent.putExtra("selectedType", selectedType);
            intent.putExtra("name", name);
            intent.putExtra("phone", phone);
            intent.putExtra("account", selectedemail);
            startActivity(intent);
            finish();
        }
        else if (buttonId == R.id.breakbtn) {
            // Set name and phone to "-"
            name = "-";
            phone = "-";
            Intent intent = new Intent(BookingPage.this, Selectedhour.class);

            intent.putExtra("isenglish", getIntent().getStringExtra("isenglish"));
            intent.putExtra("isAdmin", getIntent().getStringExtra("isAdmin"));
            intent.putExtra("isFriday", getIntent().getBooleanExtra("isFriday", false));
            intent.putExtra("selectedDate", getIntent().getStringExtra("selectedDate"));
            intent.putExtra("today", getIntent().getStringExtra("todayDateString"));
            intent.putExtra("myhour", getIntent().getStringExtra("currentTime"));
            intent.putExtra("selectedBarber", selectedBarber);
            intent.putExtra("selectedType", selectedType);
            intent.putExtra("name", name);
            intent.putExtra("phone", phone);
            intent.putExtra("account", selectedemail);
            startActivity(intent);
            finish();
        }
    }

}