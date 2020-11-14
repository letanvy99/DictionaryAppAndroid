package com.example.projectgroup.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }
    public void open() {
        this.database = openHelper.getWritableDatabase();
        Log.d("DEBUG","opendatabase");
    }

    public void close() {
        if (database != null) {
            this.database.close();
            Log.d("DEBUG","closedatabase");
        }

    }

    public ArrayList<AnhViet> getWords() {
        ArrayList<AnhViet> list = new ArrayList<>();
        Log.d("DEBUG","readdatabase");
        Cursor cursor = database.rawQuery("SELECT * FROM anh_viet limit 100", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new AnhViet(cursor.getInt(cursor.getColumnIndex("id")),cursor.getString(cursor.getColumnIndex("word")),cursor.getString(cursor.getColumnIndex("content"))));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public ArrayList<AnhViet> getWords(String filter) {
        ArrayList<AnhViet> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM anh_viet where word like '" + filter + "%' limit 10", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new AnhViet(cursor.getInt(cursor.getColumnIndex("id")),cursor.getString(cursor.getColumnIndex("word")),cursor.getString(cursor.getColumnIndex("content"))));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public String getDefinition(String word) {
        String definition = "";
        Cursor cursor = database.rawQuery("SELECT * FROM anh_viet where word='" + word + "'", null);
        cursor.moveToFirst();
        definition = cursor.getString(2);
        cursor.close();
        return definition;
    }
}
