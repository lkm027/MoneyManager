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

import java.sql.SQLData;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucas on 5/29/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    //Database
    public static final String DB_NAME = "Database";
    public static final int DATABASE_VERSION = 1;

    //Tables
    public static final String GOALS_TABLE = "GoalsTable";
    public static final String AMOUNT_TABLE = "AmountTable";

    //Columns for Amount Table
    public static final String KEY_AMOUNT = "Amount";

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

        db.execSQL(CREATE_AMOUNT_TABLE);
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
    }


    /**
     * Add the amount of money the user has spent
     * @param amount
     */
    public void addAmount(float amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_AMOUNT, amount);
        db.insert(AMOUNT_TABLE, null, values);
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
}
