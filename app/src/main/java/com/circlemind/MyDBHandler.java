package com.circlemind;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper {

//    Information Database

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "voice.db";
    private static final String TABLE_VOICE = "Voice";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_PATH = "path";

    //    Initialize the database
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int vaersion) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_VOICE_TABLE = "CREATE TABLE " +
                TABLE_VOICE + "("
                + COLUMN_NAME + " TEXT," + COLUMN_TIME
                + " TEXT," + COLUMN_PATH + " TEXT" + ")";
        db.execSQL(CREATE_VOICE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOICE);
        onCreate(db);
    }

    public void addVoice(Voice voice) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, voice.getVoiceName());
        values.put(COLUMN_TIME, voice.getVoiceTime());
        values.put(COLUMN_PATH, voice.getVoicePath());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_VOICE, null, values);
        db.close();
    }

    public Voice findVoice(String voiceName) {
        String query = "Select * FROM " + TABLE_VOICE + " WHERE " + COLUMN_NAME + " =  \"" + voiceName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Voice voice = new Voice();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            voice.setVoiceName(cursor.getString(0));
            voice.setVoiceTime(cursor.getString(1));
            voice.setVoicePath(cursor.getString(2));
            cursor.close();
        } else {
            voice = null;
        }
        db.close();
        return voice;
    }

    public boolean deleteVoice(String voiceName) {

        boolean result = false;

        String query = "Select * FROM " + TABLE_VOICE + " WHERE " + COLUMN_NAME + " =  \"" + voiceName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Voice voice = new Voice();

        if (cursor.moveToFirst()) {
            voice.setVoiceName(cursor.getString(0));
            db.delete(TABLE_VOICE, COLUMN_NAME + " = ?",
                    new String[] { voice.getVoiceName() });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }


}
