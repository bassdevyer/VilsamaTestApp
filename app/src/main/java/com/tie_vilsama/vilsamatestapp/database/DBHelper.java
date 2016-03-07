package com.tie_vilsama.vilsamatestapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tie_vilsama.vilsamatestapp.database.DBContract.User;

/**
 * Created by mac on 4/3/16.
 */
public class DBHelper extends SQLiteOpenHelper{

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_USER_TABLE =
            "CREATE TABLE " + User.TABLE_NAME + " (" +
            User._ID + " INTEGER PRIMARY KEY, " +
            User.COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA_SEP +
            User.COLUMN_NAME_PASSWORD + TEXT_TYPE + ")";

    private static final String SQL_DELETE_USER_TABLE =
            "DROP TABLE IF EXISTS " + User.TABLE_NAME;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "VilsamaApp.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_USER_TABLE);
        onCreate(db);
    }

}
