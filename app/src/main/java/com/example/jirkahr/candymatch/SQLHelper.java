package com.example.jirkahr.candymatch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "levels_table_old2";
    public static final String COL0 = "levelID";
    public static final String COL1 = "numOfMoves";
    public static final String COL2 = "getScoreWood";
    public static final String COL3 = "getScoreGold";
    public static final String COL4 = "getScoreCrystal";
    public static final String COL5 = "getScoreStone";


    public SQLHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + TABLE_NAME + " (" + COL0 + " INTEGER PRIMARY KEY, " +
                COL1 + " INTEGER," + COL2 + " INTEGER, " + COL3 + " INTEGER, " +
                COL4 + " INTEGER, " + COL5 + " INTEGER);";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(drop);

        onCreate(db);
    }

    public boolean insertData(int lvlID, int moves, int wood, int gold, int crystal, int stone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL0, lvlID);
        cv.put(COL1, moves);
        cv.put(COL2, wood);
        cv.put(COL3, gold);
        cv.put(COL4, crystal);
        cv.put(COL5, stone);

        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor selectData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

}
