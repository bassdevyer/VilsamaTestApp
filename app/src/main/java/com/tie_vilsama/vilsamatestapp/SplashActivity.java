package com.tie_vilsama.vilsamatestapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.tie_vilsama.vilsamatestapp.database.DBContract.User;
import com.tie_vilsama.vilsamatestapp.database.DBHelper;

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

        private static final String USERNAME_VALUE = "user";
        private static final String PASSWORD_VALUE = "ee11cbb19052e40b07aac0ca060c23ee";

        private static final String LOG_TAG = "DBTask";

        @Override
        protected Long doInBackground(Void... params) {
            DBHelper dbHelper = new DBHelper(SplashActivity.this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }

            // Check user register existence
            String[] projection = {
                    User._ID,
                    User.COLUMN_NAME_USERNAME
            };

            String selection = User.COLUMN_NAME_USERNAME + " = ?";

            String[] selectionArgs = {USERNAME_VALUE};

            Cursor cursor = db.query(User.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

            if(cursor.moveToFirst()){
                Log.i(LOG_TAG, "Register already inserted, skipping insert routine...");
                return new Long(0);
            };

            // Performs the register insert
            db = dbHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(User.COLUMN_NAME_USERNAME, USERNAME_VALUE);
            contentValues.put(User.COLUMN_NAME_PASSWORD, PASSWORD_VALUE);

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
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    SplashActivity.this.startActivity(intent);
                }
            }
        }
    }
}
