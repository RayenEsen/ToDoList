package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Description extends AppCompatActivity {

    private String email;
    private String message;
    private int id;
    private DB_Sqlit db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        db = DB_Sqlit.getInstance(this);
        // Retrieve data from Intent
        id = getIntent().getIntExtra("TaskId", 1);
        message = getIntent().getStringExtra("Message");
        email = getIntent().getStringExtra("Email");
        TextView TV = (TextView) findViewById(R.id.Title);
        TV.setText(message);
        EditText ETX = findViewById(R.id.Description);
        String description = ETX.getText().toString();
        TaskDescription d = new TaskDescription(email, message, description);
        d.setId(id);
        String taskdescription = d.selectTasksByIdAndEmail(db);
        ETX.setText(taskdescription);
    }

    public void save(View v) {
        EditText ETX = findViewById(R.id.Description);
        String description = ETX.getText().toString();

        if (!description.isEmpty()) {
            // Use the 'email', 'message', and 'description' as needed
            TaskDescription d = new TaskDescription(email, message, description);
            d.setId(id);
            d.save(db);
        } else {
            Toast.makeText(this, "Description should not be empty", Toast.LENGTH_LONG).show();
        }
    }
}
