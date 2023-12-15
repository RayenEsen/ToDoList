// User.java
package com.example.login;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class User {
    private String Username;
    private String Email;
    private String Password;
    private String Phone;
    private String date;

    public User() {
    }

    public User(String email, String password) {
        this.Email = email;
        this.Password = password;
    }

    public User(String Username, String Email, String Password, String Phone) {
        this.Username = Username;
        this.Email = Email;
        this.Password = Password;
        this.Phone = Phone;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private boolean userExists(SQLiteDatabase db) {
        String phoneNumber = getPhone();
        String query = "SELECT COUNT(*) FROM user WHERE Phone = ?";
        String[] selectionArgs = {phoneNumber};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        try {
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                return count > 0;  // If count is greater than 0, user with the phone number exists
            }
        } finally {
            cursor.close();
        }

        return false;
    }


    public boolean registerUser(DB_Sqlit dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(userExists(db))
        {
            return false;
        }
        else
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put("Username", getUsername());
            contentValues.put("Email", getEmail());
            contentValues.put("Password", getPassword());
            contentValues.put("Phone", getPhone());
            contentValues.put("Birthday", "2023-12-02");  // Provide a valid date or set it to NULL if not available
            long result = db.insert("user", null, contentValues);
            return result != -1;
        }
    }

    public boolean loginUser(DB_Sqlit dbHelper) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = "Email = ? AND Password = ?";
        String[] selectionArgs = {getEmail(), getPassword()};

        Cursor cursor = db.query(
                "user",
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int count = cursor.getCount();
        cursor.close();  // Close the cursor after retrieving the count

        return count > 0;
    }


}
