package com.example.developer.newnote;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.developer.newnote.database.MyDB;

import java.util.ArrayList;
import java.util.List;


public class NoteApp extends Application {
    private List<Note> notes;
    MyDB myDB;
    SQLiteDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        myDB = new MyDB(this);
        database = myDB.getWritableDatabase();
        notes = new ArrayList<>();
        getNotes().addAll(readAll());
    }

    public void create(Note note) {
        String sql = String.format("INSERT INTO tbl_note(title,desc) VALUES('%S','%S')", note.getTitle(), note.getDesc());
        database.execSQL(sql);
    }
    public List<Note> readAll(){
        List<Note> notes = new ArrayList<>();
        String sql = String.format("SELECT ID,title,desc FROM tbl_note");
        Cursor cursor= database.rawQuery(sql,null);

        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndex("ID"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String desc = cursor.getString(cursor.getColumnIndex("desc"));
                notes.add(new Note(title,desc,id));
            }while (cursor.moveToNext());
        }
        return notes;

    }

    public void update(Note note){

        database.execSQL("UPDATE tbl_note SET title='"+note.getTitle()+"' ,desc='"+note.getDesc()+"' WHERE ID="+note.getId()+";");
    }
    public void delete(Note note){
        //String sql = String.format("DELETE FROM tbl_notes WHERE ID = $s;", id+"");
        database.execSQL("DELETE FROM tbl_note WHERE ID="+note.getId()+";");
    }
    @Override
    public void onTerminate() {
        myDB.close();
        database.close();
        myDB = null;
        super.onTerminate();
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}