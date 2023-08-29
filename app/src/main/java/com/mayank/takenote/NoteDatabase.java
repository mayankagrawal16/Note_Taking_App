package com.mayank.takenote;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Note.class},version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    //we use instance (object of NoteDatabase) everyWhere in application
    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context)
    {
        if(instance==null)
        {
            instance= Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class,
                    "note_database").addCallback(roomCallback).fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public static RoomDatabase.Callback roomCallback=new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            NoteDao noteDao = instance.noteDao();
            ExecutorService executorService = Executors.newSingleThreadExecutor();

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    noteDao.insert(new Note("Title1", "Description1"));
                    noteDao.insert(new Note("Title2", "Description2"));
                    noteDao.insert(new Note("Title3", "Description3"));
                    noteDao.insert(new Note("Title4", "Description4"));
                    noteDao.insert(new Note("Title5", "Description5"));
                    noteDao.insert(new Note("Title6", "Description6"));
                }
            });
            super.onCreate(db);
        }
    };

}
