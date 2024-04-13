package com.example.haircutbooking;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    Button confirm, register;
    TextView welcome, signin;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String isAdminFlag = "no";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);
        String isEnglish = getIntent().getStringExtra("isenglish");
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        confirm = findViewById(R.id.confirmbtn);
        register = findViewById(R.id.registerbtn);
        welcome = findViewById(R.id.welcome);
        signin = findViewById(R.id.signin);
        if (isEnglish.equals("no")) {
            welcome.setText("ברוכים הבאים לקביעת תספורת");
            signin.setText("בבקשה, התחבר/י :");
            editTextEmail.setHint("אימייל");
            editTextPassword.setHint("סיסמא");
            confirm.setText("אישור");
            register.setText("או הירשם/י");
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());
                if (TextUtils.isEmpty(email)) {
                    if (isEnglish.equals("no")) {
                        Toast.makeText(LoginPage.this, "הקלד/י אימייל", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginPage.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    if (isEnglish.equals("no")) {
                        Toast.makeText(LoginPage.this, "הקלד/י סיסמא", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginPage.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String lowerCaseEmail = email.toLowerCase();
                                    if (lowerCaseEmail.equals("drorbakal17@gmail.com")){
                                        isAdminFlag = "yes";
                                    }
                                    if (lowerCaseEmail.equals("maytrabelsi1111@gmail.com")){
                                        isAdminFlag = "yes";
                                    }
                                    if (lowerCaseEmail.equals("ofekdaida16@gmail.com")){
                                        isAdminFlag = "yes";
                                    }
                                    Intent intent = new Intent(LoginPage.this, MainActivity.class);
                                    intent.putExtra("isAdminFlag", isAdminFlag);
                                    intent.putExtra("account", lowerCaseEmail);
                                    intent.putExtra("isenglish", isEnglish);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    if (isEnglish.equals("no")) {
                                        Toast.makeText(LoginPage.this, "אחד מהנתונים שהזנת שגויים", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(LoginPage.this, "One Of Your Details Wrong.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });
    }

    public void funcButton(View view) {
        int buttonId = view.getId();
        if (buttonId == R.id.registerbtn) {
            Intent intent = new Intent(LoginPage.this, RegisterPage.class);
            intent.putExtra("isenglish", getIntent().getStringExtra("isenglish"));
            startActivity(intent);
            finish();
        }
    }

}
