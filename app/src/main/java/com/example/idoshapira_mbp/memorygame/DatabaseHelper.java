package com.example.idoshapira_mbp.memorygame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by idoshapira-mbp on 25/05/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "people_score";
    private static final String COL0 = "ID";
    private static final String COL1 = "name";
    private static final String COL2 = "diff";
    private static final String COL3 = "score";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP IF TABLE EXISTS "+TABLE_NAME;
        db.execSQL(dropTable);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+ COL1+" TEXT, "+COL3+" LONG, "+COL2+" INTEGER)";
        db.execSQL(createTable);

    }



    public boolean addData(String name,int diff,long score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1,name);
        contentValues.put(COL3,score);
        contentValues.put(COL2,diff);


        Log.d(TAG, "addData: Adding "+name+" to "+TABLE_NAME);

        long result = db.insert(TABLE_NAME,null,contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public Cursor getEasyTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE diff=0";
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public Cursor getMediumTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE diff=1";
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public Cursor getHardTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE diff=2";
        Cursor data = db.rawQuery(query,null);
        return data;
    }
}
