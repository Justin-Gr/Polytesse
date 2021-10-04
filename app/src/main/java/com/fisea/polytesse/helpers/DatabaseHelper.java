package com.fisea.polytesse.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "slang_words_count";
    private static final String COL1 = "origin";
    private static final String COL2 = "count";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL1 + " VARCHAR PRIMARY KEY, " + COL2 + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void saveSlangWordsCountForOrigin(String origin, int count) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL1 + "=?";
        boolean edit = false;
        int total = count;
        try (Cursor cursor = db.rawQuery(query, new String[]{origin})) {
            if (cursor.moveToFirst()) {
                edit = true;
                total += cursor.getInt(1);
            }
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, origin);
        contentValues.put(COL2, total);

        if (edit) {
            db.update(TABLE_NAME, contentValues, COL1 + "=?", new String[] { origin });
        } else {
            db.insert(TABLE_NAME, null, contentValues);
        }

        db.close();
    }

    public Map<String, Integer> getSlangWordsCountList(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        Map<String, Integer> map = new HashMap<>();

        if (cursor.moveToFirst()) {
            do {
                map.put(cursor.getString(0), cursor.getInt(1));
            } while (cursor.moveToNext());
        }

        db.close();
        return map;
    }

    public void clearSlangWordsCountList() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}
