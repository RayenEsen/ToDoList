package com.example.login;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TaskDescription extends Task {
    private String Description;

    public TaskDescription(String email, String message, String description) {
        super(email, message);
        Description = description;
    }

    public TaskDescription() {
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    // Insert data into TaskDescription table
    public void save(DB_Sqlit db) {
        try {
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("Id",getId());
            values.put("Email", getEmail());
            values.put("Description", getDescription());

            // Insert data into TaskDescription table
            sqLiteDatabase.insert("TaskDescription", null, values);

            Log.d("TASK_DESCRIPTION_SAVE", "Data saved successfully");
        } catch (Exception e) {
            Log.e("TASK_DESCRIPTION_SAVE_ERROR", "Error saving data", e);
        }
    }
    public String selectTasksByIdAndEmail(DB_Sqlit db) {
        try {
            SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();

            String query = "SELECT Description FROM TaskDescription WHERE Email = ? AND Id = ?";
            String[] selectionArgs = {getEmail(), String.valueOf(getId())};

            Cursor cursor = sqLiteDatabase.rawQuery(query, selectionArgs);

            if (cursor != null && cursor.moveToFirst()) {
                // Check if the "Description" column exists in the cursor
                int columnIndex = cursor.getColumnIndex("Description");
                if (columnIndex != -1) {
                    // Retrieve the description from the cursor
                    String description = cursor.getString(columnIndex);

                    // Close the cursor
                    cursor.close();

                    return description;
                } else {
                    Log.e("SELECT_TASKS_ERROR", "Column 'Description' not found in the cursor");
                }
            } else {
                Log.e("SELECT_TASKS_ERROR", "No task found");
            } 

        } catch (Exception e) {
            Log.e("SELECT_TASKS_ERROR", "Error selecting tasks", e);
        }

        return null;
    }


}
