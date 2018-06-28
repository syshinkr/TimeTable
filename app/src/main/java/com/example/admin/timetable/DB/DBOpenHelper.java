package com.example.admin.timetable.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper {
    private static final String DATABASE_NAME = "ani.db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    public DBOpenHelper(Context mCtx) {
        this.mCtx = mCtx;
    }

    public DBOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        mDB.close();
    }

    public long insertColumn(int index, String title, String time, String genre,
                             String link, boolean absent, String sd, String ed, int favorite) {
        ContentValues values = new ContentValues();
        values.put(MyDB.Create.ID, index);
        values.put(MyDB.Create.TITLE, title);
        values.put(MyDB.Create.TIME, time);
        values.put(MyDB.Create.GENRE, genre);
        values.put(MyDB.Create.LINK, link);
        values.put(MyDB.Create.ABSENT, absent);
        values.put(MyDB.Create.SD, sd);
        values.put(MyDB.Create.ED, ed);
        values.put(MyDB.Create.FAVORITE, favorite);

        return mDB.insert(MyDB.Create._TABLENAME, null, values);
    }
    public boolean updateColumn(int index, String title, String time, String genre,
                                String link, boolean absent, String sd, String ed) {
        ContentValues values = new ContentValues();
        values.put(MyDB.Create.ID, index);
        values.put(MyDB.Create.TITLE, title);
        values.put(MyDB.Create.TIME, time);
        values.put(MyDB.Create.GENRE, genre);
        values.put(MyDB.Create.LINK, link);
        values.put(MyDB.Create.ABSENT, absent);
        values.put(MyDB.Create.SD, sd);
        values.put(MyDB.Create.ED, ed);

        return mDB.update(MyDB.Create._TABLENAME, values, MyDB.Create.ID + "=" + index, null) > 0;
    }
    public boolean updateFavorite(int index, int favorite) {
        ContentValues values = new ContentValues();
        values.put(MyDB.Create.FAVORITE, favorite);

        return mDB.update(MyDB.Create._TABLENAME, values, MyDB.Create.ID + "=" + index, null) > 0;
    }
    public Cursor getAllColumn() {
        return mDB.query(MyDB.Create._TABLENAME, null, null, null, null, null, null);
    }
    public boolean deleteAllColumn() {
        return mDB.delete(MyDB.Create._TABLENAME, null, null) > 0;
    }


    private class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(MyDB.Create._CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + MyDB.Create._TABLENAME);
            onCreate(db);
        }
    }
}
