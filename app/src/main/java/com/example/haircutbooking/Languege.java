package com.example.haircutbooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.haircutbooking.LoginPage;
import com.example.haircutbooking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



public class Languege extends AppCompatActivity {

    String isenglish = "yes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.languege);


        ImageView hebrewImageView = findViewById(R.id.hebrew);
        hebrewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isenglish = "no";
                // Navigate to LoginPage
                Intent intent = new Intent(Languege.this, LoginPage.class);
                intent.putExtra("isenglish", isenglish);
                startActivity(intent);
            }
        });
        ImageView englishImageView = findViewById(R.id.english);
        englishImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isenglish = "yes";
                // Navigate to LoginPage
                Intent intent = new Intent(Languege.this, LoginPage.class);
                intent.putExtra("isenglish", isenglish);
                startActivity(intent);
            }
        });
    }
}
