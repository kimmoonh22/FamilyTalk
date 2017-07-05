package com.example.a0104.mhtalk.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * Created by a0104 on 2017-07-04.
 */

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USERINFO (usrid TEXT PRIMARY KEY, isLogin TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String usrid, String isLogin) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO USERINFO VALUES('" + usrid + "', '" + isLogin + "');");
        db.close();
    }

    public void update(String usrid, String isLogin) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE USERINFO SET isLogin='" + isLogin + "' WHERE usrid='" + usrid + "';");
        db.close();
    }

    public void delete(String item) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM USERINFO WHERE usrid='" + item + "';");
        db.close();
    }

    public HashMap<String, String> getResult() {

        SQLiteDatabase db = getReadableDatabase();
        String id = "";
        String isLogin = "";
        HashMap<String, String> result = new HashMap<String, String>();

        Cursor cursor = db.rawQuery("SELECT * FROM USERINFO", null);
        while (cursor.moveToNext()) {
            id = cursor.getString(0);
            isLogin = cursor.getString(1);
        }
        result.put("usrid", id);
        result.put("isLogin", isLogin);

        return result;
    }
}
