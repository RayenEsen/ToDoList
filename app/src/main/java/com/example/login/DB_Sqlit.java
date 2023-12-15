package com.example.login;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DB_Sqlit extends SQLiteOpenHelper {
    private static final String BDname = "data.db";
    private static DB_Sqlit instance;  // Database instance

    private DB_Sqlit(Context context) {
        super(context, BDname, null, 1);
    }

    public static synchronized DB_Sqlit getInstance(Context context) {
        if (instance == null) {
            instance = new DB_Sqlit(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String createUserTableQuery = "CREATE TABLE user (" +
                    "Phone varchar(8) NOT NULL," +
                    "Username varchar(30) NOT NULL," +
                    "Email varchar(30) PRIMARY KEY NOT NULL," +
                    "Password varchar(30) NOT NULL," +
                    "Birthday date" +
                    ")";
            db.execSQL(createUserTableQuery);

            String createTaskTableQuery = "CREATE TABLE task (" +
                    "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Email varchar(30) NOT NULL," +
                    "Message varchar(255) NOT NULL," +
                    "FOREIGN KEY (Email) REFERENCES user(Email)" +
                    ")";
            db.execSQL(createTaskTableQuery);

            String createTaskDescriptionQuery = "CREATE TABLE TaskDescription (" +
                    "Id INTEGER PRIMARY KEY," +
                    "Email varchar(30) NOT NULL," +
                    "Description varchar(255)," +  // Fix: Added the missing comma
                    "FOREIGN KEY (Email) REFERENCES user(Email)," +
                    "FOREIGN KEY (Id) REFERENCES task(Id)" +
                    ")";
            db.execSQL(createTaskDescriptionQuery);

            Log.d("DB_CREATION", "Tables created successfully");
        } catch (Exception e) {
            Log.e("DB_CREATION_ERROR", "Error creating tables", e);
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }
}
