package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.util.Patterns;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity {
    private DB_Sqlit db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = DB_Sqlit.getInstance(this);
    }
    public void Login(View V) {
        TextView t = findViewById(R.id.email);
        String email = t.getText().toString();
        TextView t2 = findViewById(R.id.Email_Error);
        TextView p = findViewById(R.id.password);
        TextView p2 = findViewById(R.id.Password_Error);
        String password = p.getText().toString();

        if (email.isEmpty()) {
            t2.setVisibility(View.VISIBLE);
            t2.setText("Email empty");
        } else if (!isValidEmail(email)) {
            t2.setVisibility(View.VISIBLE);
            t2.setText("Email invalid");
        } else {
            t2.setVisibility(View.GONE);
        }

        if (password.isEmpty()) {
            p2.setVisibility(View.VISIBLE);
            p2.setText("Password empty");
        } else if (!isStrongPassword(password)) {
            p2.setVisibility(View.VISIBLE);
            p2.setText("Password not strong");
        } else {
            p2.setVisibility(View.GONE);

            // If email and password are valid, set up connection / Verify him and then head to the main page
            if (t2.getVisibility() == View.GONE && p2.getVisibility() == View.GONE) {
                    User user = new User(email,password);
                    if(user.loginUser(db))
                    {
                        Intent intent = new Intent(MainActivity.this, Choice.class);
                        intent.putExtra("Email", email);
                        startActivity(intent);
                    }else {
                        Toast.makeText(this, "User does not exist", Toast.LENGTH_LONG).show();
                    }
            }
        }
    }
    public void RegisterPage(View v)
    {
        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);
    }


    private boolean isValidEmail(CharSequence target) {
        return (Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    private boolean isStrongPassword(String password) {
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        return password.matches(passwordPattern);
    }

}