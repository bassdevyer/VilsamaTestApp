package com.tie_vilsama.vilsamatestapp.database;

import android.provider.BaseColumns;

/**
 * Created by mac on 4/3/16.
 */
public final class DBContract {

    public static abstract class User implements BaseColumns{
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";

    }

}
