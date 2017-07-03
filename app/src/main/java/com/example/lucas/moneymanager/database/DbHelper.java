package com.example.lucas.moneymanager.database;

/**
 * Created by Lucas on 6/27/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.lucas.moneymanager.classes.Item;

import java.sql.SQLData;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucas on 5/29/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    //Database
    public static final String DB_NAME = "Database";
    public static final int DATABASE_VERSION = 2;

    //Tables
    public static final String GOALS_TABLE = "GoalsTable";
    public static final String AMOUNT_TABLE = "AmountTable";
    private static final String ITEM_TABLE = "ItemTable";

    //Columns for Amount Table
    public static final String KEY_AMOUNT = "Amount";

    //Columns for Item Table
    public static final String KEY_ITEM = "Item";
    public static final String KEY_ITEMAMOUNT = "ItemAmount";

    //columns for goals table
    public static final String KEY_GOALNAME = "GoalName";
    public static final String KEY_TYPE = "Type";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates our tables within our db
     * @param db The database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
//        String CREATE_GOAL_TABLE = "CREATE TABLE " + GOALS_TABLE + "(" + KEY_GOALNAME
//                + " TEXT," + KEY_TYPE + " TEXT)";

        String CREATE_AMOUNT_TABLE = "CREATE TABLE " + AMOUNT_TABLE + "(" + KEY_AMOUNT +
                " REAL)";

        String CREATE_ITEM_TABLE = "CREATE TABLE " + ITEM_TABLE + "(" + KEY_ITEM + " TEXT,"
                + KEY_ITEMAMOUNT + " REAL)";

        db.execSQL(CREATE_AMOUNT_TABLE);
        db.execSQL(CREATE_ITEM_TABLE);
    }

    /**
     * Performs some action on our database when we change the version of our table
     * @param db The databse
     * @param oldVersion oldversion of the database
     * @param newVersion newversion of the database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // If you need to add a column
//        if (newVersion > oldVersion) {
//            db.execSQL("ALTER TABLE GoalsTable ADD COLUMN new_column INTEGER DEFAULT 0");
//        }
        db.execSQL("DROP TABLE IF EXISTS " + AMOUNT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ITEM_TABLE);
        onCreate(db);
    }


    /**
     * Add the amount of money the user has spent
     * @param amount
     */
    public void updateAmount(float amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_AMOUNT, amount);
        db.insert(AMOUNT_TABLE, null, values);
        db.close();
    }

    /**
     * Updates our item table with the correct items bought and their corresponding price
     * @param amount
     * @param item
     */
    public void updateItems(float amount, String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ITEM, item);
        values. put(KEY_ITEMAMOUNT, amount);
        db.insert(ITEM_TABLE, null, values);
        db.close();
    }

    /**
     * Deletes our amountSpent table within our database
     */
    public void deleteAmount() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(AMOUNT_TABLE, null, null);
        db.close();
    }

    /**
     * Grab the current amount ofm money the user has spent
     * @return amount spent to date
     */
    public float getAmount() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " + AMOUNT_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        float amount = cursor.moveToFirst() ? cursor.getInt(0) : 0;
        db.close();
        return amount;
    }

    /**
     * Retrieves all the items in our current database
     * @return list of our goals
     */
    public List<Item> getAllGoals() {
        List<Item> list = new ArrayList<>();

        //Select all query
        String query = "SELECT * FROM " + ITEM_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        //Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(new Item(cursor.getString(0), cursor.getFloat(1)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * Deletes all of our data from our two tables; A hard reset
     */
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(AMOUNT_TABLE, null, null);
        db.delete(ITEM_TABLE, null, null);
        db.close();
    }
}
