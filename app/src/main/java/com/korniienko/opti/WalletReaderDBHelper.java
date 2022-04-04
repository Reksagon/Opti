package com.korniienko.opti;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class WalletReaderDBHelper extends SQLiteOpenHelper {


    public WalletReaderDBHelper(Context context) {
        // конструктор суперкласса
        super(context, "WalletDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // создаем таблицу с полями
        db.execSQL("create table wallets ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "cost integer,"
                + "date text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}


