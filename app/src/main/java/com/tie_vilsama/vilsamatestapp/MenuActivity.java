package com.tie_vilsama.vilsamatestapp;

import android.app.Activity;
import android.content.Intent;
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
}
