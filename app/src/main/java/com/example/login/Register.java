package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Patterns;
import android.view.View;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

@SuppressLint("NotConstructor")
public class Register extends AppCompatActivity {
    private DB_Sqlit db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = DB_Sqlit.getInstance(this);
    }
    public void RegisterUser(View v) {
        TextView t = findViewById(R.id.Username);
        String username = t.getText().toString();
        TextView u = findViewById(R.id.usernameError);
        if (username.isEmpty()) {
            u.setVisibility(View.VISIBLE);
            u.setText("Username obligatory");
        } else {
            u.setVisibility(View.GONE);
        }

        TextView t2 = findViewById(R.id.Email);
        String email = t2.getText().toString();
        TextView u2 = findViewById(R.id.EmailError);
        if (email.isEmpty()) {
            u2.setVisibility(View.VISIBLE);
            u2.setText("Email obligatory");
        } else if (!isValidEmail(email)) {
            u2.setVisibility(View.VISIBLE);
            u2.setText("Email invalid");
        } else {
            u2.setVisibility(View.GONE);
        }

        TextView t3 = findViewById(R.id.Password);
        String password = t3.getText().toString();
        TextView u3 = findViewById(R.id.PasswordError);
        if (password.isEmpty()) {
            u3.setVisibility(View.VISIBLE);
            u3.setText("Password Obligatory");
        } else if (!isStrongPassword(password)) {
            u3.setVisibility(View.VISIBLE);
            u3.setText("Password not strong");
        } else {
            u3.setVisibility(View.GONE);
        }

        TextView t4 = findViewById(R.id.Tel);
        String phone = t4.getText().toString();
        TextView u4 = findViewById(R.id.PhoneError);
        if (phone.isEmpty()) {
            u4.setVisibility(View.VISIBLE);
            u4.setText("Phone obligatory");
        } else if (!isValidPhoneNumber(phone)) {
            u4.setText("Phone invalid");
            u4.setVisibility(View.VISIBLE);
        } else {
            u4.setVisibility(View.GONE);
        }
        TextView t5 = findViewById(R.id.Date);
        String date = t5.getText().toString();
        if (u.getVisibility() == View.GONE && u2.getVisibility() == View.GONE && u3.getVisibility() == View.GONE && u4.getVisibility() == View.GONE) {
            User user = new User(username,email,password,phone);
            if (user.registerUser(db)) {
                // Registration successful
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(Register.this,Choice.class);
                startActivity(intent);
            }
        }
    }



    private boolean isValidEmail(CharSequence target) {
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private boolean isStrongPassword(String password) {
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        return password.matches(passwordPattern);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^[0-9\\s.()-]{10,}$");
    }

    public void GoLoginPage(View V)
    {
        Intent intent = new Intent(Register.this, MainActivity.class);
        startActivity(intent);
    }
}