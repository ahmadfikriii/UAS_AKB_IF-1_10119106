package com.ahmadfikri.uas_akb_if_1_10119106;

/*
NIM     : 10119106
Nama    : Ahmad Fikri Maulana
Kelas   : IF-1/S1/VI
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabase extends SQLiteOpenHelper {

    private static final int  DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "dbnotes";
    private static final String DATABASE_TABLE = "notestables";

    //kolom untuk database

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";


    public NoteDatabase(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //CREATE TABLE nametable(id INTEGER PRIMARY KEY, title TEXT, content TEXT, category TEXT, date TEXT, time TEXT);
        String query ="CREATE TABLE "+ DATABASE_TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                KEY_TITLE + " TEXT,"+
                KEY_CONTENT + " TEXT,"+
                KEY_CATEGORY + " TEXT,"+
                KEY_DATE + " TEXT," +
                KEY_TIME + " TEXT" +")";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion){
            return;
        }else {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(sqLiteDatabase);
        }
    }

    public long addNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(KEY_TITLE, note.getTitle());
        c.put(KEY_CONTENT, note.getContent());
        c.put(KEY_CATEGORY, note.getCategory());
        c.put(KEY_DATE, note.getDate());
        c.put(KEY_TIME, note.getTime());

        long ID = db.insert(DATABASE_TABLE,null,c);
        Log.d("inserted","ID -> "+ ID);

        return ID;
    }

    public Note getNote(long id){
        // select * from databaseTable where id = 1
        SQLiteDatabase db = this.getWritableDatabase();
        String[] query = new String[]{KEY_ID,KEY_TITLE,KEY_CONTENT,KEY_CATEGORY,KEY_DATE,KEY_TIME};
        Cursor cursor = db.query(DATABASE_TABLE,query,KEY_ID+"=?",
                new String[]{String.valueOf(id)},null,null,null,null);

        if (cursor != null)
            cursor.moveToFirst();

        return new Note(Long.parseLong(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),
                cursor.getString(4), cursor.getString(5));

    }
    public List<Note> getNotes(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Note> allNotes = new ArrayList<>();
        //select * from database

        String query = "SELECT * FROM "+ DATABASE_TABLE + " ORDER BY "+ KEY_ID+ " DESC";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                Note note = new Note();
                note.setID(cursor.getLong(0));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                note.setCategory(cursor.getString(3));
                note.setDate(cursor.getString(4));
                note.setTime(cursor.getString(5));

                allNotes.add(note);

            }while (cursor.moveToNext());
        }
        return allNotes;
    }

    public int editNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        Log.d("edited", "edited title:-> "+note.getTitle()+"\n ID ->"+note.getID());
        c.put(KEY_TITLE,note.getTitle());
        c.put(KEY_CONTENT,note.getContent());
        c.put(KEY_CATEGORY,note.getCategory());
        c.put(KEY_DATE,note.getDate());
        c.put(KEY_TIME,note.getTime());
        return db.update(DATABASE_TABLE,c,KEY_ID+"=?",new String[]{String.valueOf(note.getID())});
    }

    void deleteNote(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }

}

