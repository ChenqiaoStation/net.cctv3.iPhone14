package com.iPhone14.newarchitecture.cache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import org.jetbrains.annotations.NotNull;

public class SQLiteOpener extends SQLiteOpenHelper {
    public SQLiteOpener(Context context) {
        super(context, "MISCaches", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Db: MISCaches
        // Table: `config`
        db.execSQL("create table nativeCaches(id varchar(16) primary key not null, value varchar(16))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
