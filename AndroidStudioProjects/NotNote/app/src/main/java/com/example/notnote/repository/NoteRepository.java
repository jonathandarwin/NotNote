package com.example.notnote.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.notnote.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteRepository extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "NotNote.db";
    private static final int DATABASE_VERSION = 1;
    private Context mContext;

    public NoteRepository(Context mContext){
        super(mContext, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = mContext;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE msNote(" +
                "noteId INTEGER PRIMARY KEY,"+
                "title TEXT," +
                "description TEXT," +
                "category TEXT," +
                "auditedTime TEXT," +
                "auditedActivity TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS msNote";
        db.execSQL(query);
        onCreate(db);
    }

    public boolean insert(Note note){
        SQLiteDatabase db = getWritableDatabase();

        try{
            ContentValues values = new ContentValues();
            values.put("title", note.getTitle());
            values.put("description", note.getDescription());
            values.put("category", note.getCategory());
            values.put("auditedTime", note.getAuditedTime());
            values.put("auditedActivity", "I");

            db.insert("msNote", null, values);
        }
        catch(Exception e){
            Log.d("<ERR>", "<ERR> " + e.toString());
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public boolean update(Note note){
        SQLiteDatabase db = getWritableDatabase();

        try{
            String query = "UPDATE msNote SET " +
                    "title = \"" + note.getTitle() + "\"," +
                    "description = \"" + note.getDescription() + "\"," +
                    "category = \"" + note.getCategory() + "\"," +
                    "auditedTime = \"" + note.getAuditedTime() + "\"," +
                    "auditedActivity = \"U\" " +
                    "WHERE noteId = " + note.getNoteId();

            db.execSQL(query);
        }
        catch(Exception e){
            Log.d("<ERR>", "<ERR> " + e.toString());
            db.close();
            return false;
        }
        db.close();
        return true;
    }


    public List<Note> getByCategory(String category){
        SQLiteDatabase db = getWritableDatabase();
        List<Note> listNote = new ArrayList<>();
        try{
            String query = "SELECT * FROM msNote WHERE category = \"" + category + "\" AND auditedActivity != \"D\" ORDER BY auditedTime DESC";
            Cursor c = db.rawQuery(query, null);
            c.moveToFirst();

            while(!c.isAfterLast()){
                Note note = new Note();
                note.setNoteId(c.getInt(c.getColumnIndex("noteId")));
                note.setTitle(c.getString(c.getColumnIndex("title")));
                note.setDescription(c.getString(c.getColumnIndex("description")));
                note.setCategory(c.getString(c.getColumnIndex("category")));
                note.setAuditedTime(c.getString(c.getColumnIndex("auditedTime")));
                note.setAuditedActivity(c.getString(c.getColumnIndex("auditedActivity")));

                listNote.add(note);
                c.moveToNext();
            }
            db.close();
            return listNote;
        }
        catch(Exception e){
            Log.d("<ERR>", "<ERR> " + e.toString());
            db.close();
            return new ArrayList<>();
        }
    }

    public boolean delete(int IDNote){
        SQLiteDatabase db = getWritableDatabase();

        try{
            String query = "DELETE FROM msNote WHERE noteId = " + IDNote;
            db.execSQL(query);
        }
        catch(Exception e){
            Log.d("<ERR>", "<ERR> " + e.toString());
            db.close();
            return false;
        }
        db.close();
        return true;
    }
}
