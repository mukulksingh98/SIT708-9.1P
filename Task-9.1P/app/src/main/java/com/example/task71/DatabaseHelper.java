package com.example.task71;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String MY_DATABASE = "lostfound.db";
    public static final int VERSION = 1;

    public static final String TABLE_NAME = "items";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_LOCATION = "location";

    public static final String COLUMN_LATLNG = "latlng";



    public DatabaseHelper(@Nullable Context context) {
        super(context, MY_DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("  +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_LOCATION + " TEXT, " +
                COLUMN_LATLNG + " TEXT)";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DROP_TABLE);
        onCreate(db);

    }

    public long insertItem(Item item) {
        //get the database
        SQLiteDatabase db = this.getWritableDatabase();
        //get the values from the input item variable
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, item.getName());
        contentValues.put(COLUMN_TYPE, item.getType());
        contentValues.put(COLUMN_PHONE, item.getPhone());
        contentValues.put(COLUMN_DESCRIPTION, item.getDescription());
        contentValues.put(COLUMN_DATE, item.getDate());
        contentValues.put(COLUMN_LOCATION, item.getLocation());
        contentValues.put(COLUMN_LATLNG, item.getLatlng());
        //create new row in the database with the values
        long newRowId = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return newRowId;

    }

    public void deleteItem(int id)
    {
        //get the database and delete by column id
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "='" + id +"'");
        db.close();
    }

    public List<Item> getAllItems() {
        Log.v("getallitems", "true");

        List<Item> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        //get all items from database query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        String name, type, phone, description, date, location, latlng;
        int id;

        //iterate over the database adding to the items List to be used within the program
        if(cursor.moveToFirst()) {
            do {
                Log.v("database", cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE));
                phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE));
                description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
                location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION));
                latlng = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LATLNG));
                Item item = new Item(id, name, type, phone, description, date, location, latlng);
                items.add(item);
            } while (cursor.moveToNext());

        }
        cursor.close();
        return items;
    }

    public List<String> getAllLatLng() {
        Log.v("getallitems", "true");

        List<String> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        //get all items from database query
        String selectQuery = "SELECT latlng FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        String latlng;

        //iterate over the database adding to the items List to be used within the program
        if(cursor.moveToFirst()) {
            do {
                latlng = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LATLNG));
                items.add(latlng);
            } while (cursor.moveToNext());

        }
        cursor.close();
        return items;
    }

}
