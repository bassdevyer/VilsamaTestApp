package com.tie_vilsama.vilsamatestapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Space;

import com.tie_vilsama.vilsamatestapp.database.DBContract.User;
import com.tie_vilsama.vilsamatestapp.database.DBHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SplashActivity extends Activity {

    private DBTask dbTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // Performs the asyncTask
        dbTask = new DBTask();
        dbTask.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbTask != null) {
            dbTask.cancel(true);
        }
    }



    private class DBTask extends AsyncTask<Void, Void, Long>{

        private static final String USERNAME_TAG = "username";
        private static final String PASSWORD_TAG = "password";
        private String usernameValue = null;
        private String passwordValue = null;

        private static final String LOG_TAG = "DBTask";

        @Override
        protected Long doInBackground(Void... params) {
            DBHelper dbHelper = new DBHelper(SplashActivity.this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            // FIXME remove
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }

            try {
                InputStream inputStream = SplashActivity.this.getAssets().open(SplashActivity.this.getString(R.string.app_properties_file_name));
                Properties properties = new Properties();
                properties.load(inputStream);
                usernameValue = properties.getProperty(USERNAME_TAG);
                passwordValue = properties.getProperty(PASSWORD_TAG);
            }
            catch (IOException ex){
                Log.e(LOG_TAG, "Cannot open app properties file!!!");
                return new Long(-1);
            }
            // Check user register existence
            String[] projection = {
                    User._ID,
                    User.COLUMN_NAME_USERNAME
            };

            String selection = User.COLUMN_NAME_USERNAME + " = ?";

            String[] selectionArgs = {usernameValue};

            Cursor cursor = db.query(User.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

            if(cursor.moveToFirst()){
                Log.i(LOG_TAG, "Register already inserted, skipping insert routine...");
                return new Long(0);
            };

            // Performs the register insert
            db = dbHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(User.COLUMN_NAME_USERNAME, usernameValue);
            contentValues.put(User.COLUMN_NAME_PASSWORD, passwordValue);

            Long newRowId = db.insert(User.TABLE_NAME, null, contentValues);
            if(newRowId == -1){
                Log.e(LOG_TAG, "Error inserting register!!!");
            }else {
                Log.i(LOG_TAG, "Register successfully inserted!!!");
            }
            return newRowId;

        }

        @Override
        protected void onPostExecute(Long l) {
            super.onPostExecute(l);
            switch (String.valueOf(l)){
                case "-1":{
                    AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                    builder.setMessage(R.string.error_message);
                    builder.setCancelable(Boolean.FALSE);
                    builder.create();
                    builder.show();
                    break;
                }
                default:{
                    // Check shared preferences for user authentication status
                    SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preferences_file_name), MODE_PRIVATE);
                    boolean isAuthenticated = sharedPreferences.getBoolean(getString(R.string.is_authenticated), Boolean.FALSE);
                    Intent intent;
                    if(isAuthenticated){
                        intent = new Intent(SplashActivity.this, MenuActivity.class);
                    }else{
                        intent = new Intent(SplashActivity.this, LoginActivity.class);
                    }
                    SplashActivity.this.startActivity(intent);

                }
            }
        }
    }
}
