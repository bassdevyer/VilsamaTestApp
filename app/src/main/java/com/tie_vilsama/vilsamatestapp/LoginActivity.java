package com.tie_vilsama.vilsamatestapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tie_vilsama.vilsamatestapp.database.DBContract;
import com.tie_vilsama.vilsamatestapp.database.DBHelper;
import com.tie_vilsama.vilsamatestapp.util.MD5Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Activity que gestiona autenticacion de usuario
 * @author mtorres
 */
public final class LoginActivity extends Activity {

    private EditText usernameEditText;
    private EditText passwordEditText;

    private static final String LOG_TAG = LoginActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEditText = (EditText) findViewById(R.id.input_username);
        passwordEditText = (EditText) findViewById(R.id.input_password);
    }

    public void login(View view){
        String username = null;
        String password = null;
        if(usernameEditText.getText() != null && !usernameEditText.getText().toString().isEmpty()){
            username = usernameEditText.getText().toString();
        }else{
            usernameEditText.setError(this.getString(R.string.activity_login_empty_username_message));
        }

        if(passwordEditText.getText() != null && !usernameEditText.getText().toString().isEmpty()){
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.update(passwordEditText.getText().toString().getBytes());
                byte[] digest = messageDigest.digest();
                password = MD5Util.toHexString(digest);
            }catch (NoSuchAlgorithmException ex){
                Log.e(LOG_TAG, "Error geting digest algorythm!!!");
            }
        }else{
            passwordEditText.setError(this.getString(R.string.activity_login_empty_username_message));
        }

        if(username != null && password != null){
            LoginTask loginTask = new LoginTask();
            loginTask.execute(username, password);
        }

    }

    private final class LoginTask extends AsyncTask<String, Void, Boolean>{
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage(LoginActivity.this.getString(R.string.activity_login_authenticating_message));
            progressDialog.setCancelable(Boolean.FALSE);
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            DBHelper dbHelper = new DBHelper(LoginActivity.this);
            // FIXME remove
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            String selection = DBContract.User.COLUMN_NAME_USERNAME + " = ? AND " + DBContract.User.COLUMN_NAME_PASSWORD + " = ?";
            String[] selectionArgs = {
                    params[0],
                    params[1]
            };
            Cursor cursor = database.query(DBContract.User.TABLE_NAME, null, selection, selectionArgs, null, null, null);
            if(cursor.moveToFirst()){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progressDialog.dismiss();
            Toast.makeText(LoginActivity.this, aBoolean.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
