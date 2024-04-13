package com.example.haircutbooking;

import android.text.TextUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterPage extends AppCompatActivity {
    TextInputEditText editTextEmail,editTextPassword;
    Button confirm;

    TextView appname;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerpage);
        String isEnglish = getIntent().getStringExtra("isenglish");
        editTextEmail = findViewById(R.id.email1);
        editTextPassword = findViewById(R.id.password1);
        appname = findViewById(R.id.appnameregister);
        confirm = findViewById(R.id.confirm);
        if (isEnglish.equals("no")) {
            appname.setText("קביעת תספורת");
            editTextEmail.setHint("אימייל");
            editTextPassword.setHint("סיסמא");
            confirm.setText("אישור");
        }
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,email,password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());
                if(TextUtils.isEmpty(email)){
                    if (isEnglish.equals("no")) {
                        Toast.makeText(RegisterPage.this,"הקלד אימייל",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        Toast.makeText(RegisterPage.this, "Enter Email", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if(TextUtils.isEmpty(password)){
                    if (isEnglish.equals("no")) {
                        Toast.makeText(RegisterPage.this,"הקלד סיסמא",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        Toast.makeText(RegisterPage.this, "Enter Password", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                firebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    if (isEnglish.equals("no")) {
                                        Toast.makeText(RegisterPage.this, "ההרשמה בוצעה בהצלחה",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(RegisterPage.this, "Registration Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    Intent intent = new Intent(RegisterPage.this, LoginPage.class);
                                    intent.putExtra("isenglish", getIntent().getStringExtra("isenglish"));
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    if (isEnglish.equals("no")) {
                                        Toast.makeText(RegisterPage.this, "ההרשמה נכשלה",
                                                Toast.LENGTH_SHORT).show();
                                        return;
                                    } else {
                                        Toast.makeText(RegisterPage.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });
    }

    public void funcButton(View view) {
        int buttonId = view.getId();
        if (buttonId == R.id.backbutton) {
            Intent intent = new Intent(RegisterPage.this, LoginPage.class);
            intent.putExtra("isenglish", getIntent().getBooleanExtra("isenglish", false));
            startActivity(intent);
            finish();
        }
    }
}
