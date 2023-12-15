package com.example.login;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.*;
public class Task {
    private int id;
    private String email;  // Change variable name to email
    private String message;

    public int getId() {
        return id;
    }

    public String getEmail() {  // Change method name to getEmail
        return email;
    }

    public String getMessage() {
        return message;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {  // Change method name to setEmail
        this.email = email;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Task(String email, String message) {
        this.email = email;
        this.message = message;
    }
    public Task(){}
    public boolean addTask(DB_Sqlit dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("Email", getEmail());  // Change field name to Email
        contentValues.put("Message", getMessage());

        long result = db.insert("task", null, contentValues);
        return result != -1;
    }
    public static List<Task> getAllTasksForEmail(DB_Sqlit dbHelper, String email) {
        List<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Log the SQL query
        String sqlQuery = "SELECT * FROM task WHERE Email = ?";
        Log.d("SQL_QUERY", sqlQuery);

        // Specify the selection arguments
        String[] selectionArgs = {email};

        try {
            // Perform the raw query
            Cursor cursor = db.rawQuery(sqlQuery, selectionArgs);

            // Check if there are any results
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Task task = new Task();
                    int idIndex = cursor.getColumnIndex("Id");
                    if (idIndex != -1) {
                        task.setId(cursor.getInt(idIndex));
                    }

                    int emailIndex = cursor.getColumnIndex("Email");
                    if (emailIndex != -1) {
                        task.setEmail(cursor.getString(emailIndex));
                    }

                    int messageIndex = cursor.getColumnIndex("Message");
                    if (messageIndex != -1) {
                        task.setMessage(cursor.getString(messageIndex));
                    }

                    // Add the task to the list
                    taskList.add(task);
                } while (cursor.moveToNext());

                // Close the cursor to release resources
                cursor.close();
            }
        } catch (Exception e) {
            Log.e("SQL_QUERY_ERROR", "Error executing SQL query", e);
        } finally {
            // Close the database connection
            db.close();
        }

        return taskList;
    }

    public void deleteTaskById(DB_Sqlit dbHelper, String email, String message) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Define the where clause
        String whereClause = "Email = ? AND Message = ?";

        // Specify the where arguments
        String[] whereArgs = {email, message};

        // Perform the deletion
        int result = db.delete("task", whereClause, whereArgs);

        // Close the database connection
        db.close();

        // Check if the deletion was successful
        if (result > 0) {
            Log.d("DELETE_TASK", "Task with Email " + email + " and Message " + message + " deleted successfully");
        } else {
            Log.e("DELETE_TASK_ERROR", "Error deleting task with Email " + email + " and Message " + message);
        }
    }




}
