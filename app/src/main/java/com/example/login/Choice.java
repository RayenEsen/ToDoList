package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Choice extends AppCompatActivity {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsadapter;
    private ListView listview;
    private FloatingActionButton button;
    private DB_Sqlit db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        db = DB_Sqlit.getInstance(this);

        listview = findViewById(R.id.listView);
        button = findViewById(R.id.fab);

        items = new ArrayList<>();
        itemsadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listview.setAdapter(itemsadapter);

        button.setOnClickListener(v -> addItem(v));

        setUpViewListener();

        // Retrieve tasks for the user's email and populate the list
        String userEmail = getIntent().getStringExtra("Email");
        List<Task> userTasks = Task.getAllTasksForEmail(db, userEmail);
        for (Task task : userTasks) {
            itemsadapter.add(task.getMessage());
        }

        // Set up item click listener
        listview.setOnItemClickListener((parent, view, position, id) -> {
            // Retrieve the Task object for the selected item
            Task selectedTask = userTasks.get(position);

            // Pass task id, message, and email to the Description activity
            Intent intent = new Intent(Choice.this, Description.class);
            intent.putExtra("TaskId", selectedTask.getId());
            intent.putExtra("Message", selectedTask.getMessage());
            intent.putExtra("Email", userEmail);
            startActivity(intent);
        });
    }

    private void setUpViewListener() {
        listview.setOnItemLongClickListener((parent, view, position, id) -> {
            String messageToDelete = items.get(position);

            // Get the user's email
            String userEmail = getIntent().getStringExtra("Email");

            // Create an instance of Task
            Task task = new Task(userEmail, messageToDelete);

            // Call the non-static method
            task.deleteTaskById(db, userEmail, messageToDelete);

            // Remove the item from the list
            items.remove(position);

            // Notify the adapter that the data set has changed
            itemsadapter.notifyDataSetChanged();

            // Show a toast indicating that the item was removed
            Toast.makeText(this, "Item Removed: " + messageToDelete, Toast.LENGTH_LONG).show();

            return true;
        });
    }

    public void addItem(View v) {
        EditText ETX = findViewById(R.id.name);
        String itemText = ETX.getText().toString();
        if (!itemText.isEmpty()) {
            itemsadapter.add(itemText);
            ETX.setText("");
            String email = getIntent().getStringExtra("Email");
            Task task = new Task(email, itemText);
            task.addTask(db);
        } else {
            Toast.makeText(this, "Please enter Text..", Toast.LENGTH_LONG).show();
        }
    }
}
