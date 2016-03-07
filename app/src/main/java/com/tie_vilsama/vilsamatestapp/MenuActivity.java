package com.tie_vilsama.vilsamatestapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void openTablesDistributionActivity(View view){
        Intent intent = new Intent(this, TablesDistributionActivity.class);
        startActivity(intent);
    }

    public void logout(View view){
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preferences_file_name), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getString(R.string.is_authenticated), Boolean.FALSE);
        editor.commit();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
