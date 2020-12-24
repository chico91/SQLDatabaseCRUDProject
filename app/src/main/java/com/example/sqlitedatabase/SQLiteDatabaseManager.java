package com.example.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class SQLiteDatabaseManager {
    SQLHelper helper;

    public SQLiteDatabaseManager(Context context) {
        helper = new SQLHelper(context);
    }

    public long insertData(String name, String address) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLHelper.NAME, name);
        contentValues.put(SQLHelper.ADDRESS, address);

        long id = db.insert(SQLHelper.TABLE_NAME, null, contentValues);
        db.close();
        return id;
    }

    public String getData() {
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] columns = {SQLHelper.UID, SQLHelper.NAME, SQLHelper.ADDRESS};
        Cursor cursor = db.query(SQLHelper.TABLE_NAME, columns, null, null, null, null, null);

        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int indexId = cursor.getColumnIndex(SQLHelper.UID);
            int indexName = cursor.getColumnIndex(SQLHelper.NAME);
            int indexAddress = cursor.getColumnIndex(SQLHelper.ADDRESS);

            int uId = cursor.getInt(indexId);
            String Name = cursor.getString(indexName);
            String Address = cursor.getString(indexAddress);
            buffer.append(uId + ":" + Name + "-" + Address +"\n");
        }
        return buffer.toString();
    }

    public String getAddress(String name) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] columns = {SQLHelper.ADDRESS};
        String[] args = {name};
        Cursor cursor = db.query(SQLHelper.TABLE_NAME, columns, SQLHelper.NAME + " = ?", args, null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int indexAddress = cursor.getColumnIndex(SQLHelper.ADDRESS);
            String Address = cursor.getString(indexAddress);
            buffer.append(Address);
        }
        return buffer.toString();
    }

    public String getUserId(String name, String address) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] columns = {SQLHelper.UID};
        String[] args = {name, address};
        Cursor cursor = db.query(SQLHelper.TABLE_NAME, columns, SQLHelper.NAME + " = ? AND " + SQLHelper.ADDRESS + "=?", args, null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int indexId = cursor.getColumnIndex(SQLHelper.UID);
            int uId = cursor.getInt(indexId);
            buffer.append(uId);
        }
        return buffer.toString();
    }

    public int updateName(String currentName, String newName){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put(SQLHelper.NAME,newName);
        String[] args = {currentName};
        int count = db.update(SQLHelper.TABLE_NAME,contentValues,SQLHelper.NAME+" = ?",args);
        return count;
    }
    public int updateAddress(String userName, String newAddress){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put(SQLHelper.ADDRESS,newAddress);
        String[] args = {userName};
        int count = db.update(SQLHelper.TABLE_NAME,contentValues,SQLHelper.NAME+" = ?",args);
        return count;
    }
    public int delete(String userName){
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] args = {userName};
        int count = db.delete(SQLHelper.TABLE_NAME,SQLHelper.NAME+" = ?", args);
        return count;
    }


    public static class SQLHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "userDatabase";
        private static final String TABLE_NAME = "myTable";
        private static final int DATABASE_VERSION = 1;
        private static final String UID = "_id";
        private static final String NAME = "name";
        private static final String ADDRESS = "address";
        private static final String PHONE= "phone";
        private static final String CREATE_TABLE = " CREATE TABLE " +TABLE_NAME+ "("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+NAME+" VARCHAR(255)," +ADDRESS+" VARCHAR(255));";
        private static final String ALTER_TABLE = " ALTER TABLE " +TABLE_NAME + " ADD COLUMN " + PHONE +"int DEFAULT 0";
        private Context context;

        public SQLHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
            Toast.makeText(context, "onCreate called", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(ALTER_TABLE);
            Toast.makeText(context, "onUpgrade called", Toast.LENGTH_LONG).show();
        }
    }
}

