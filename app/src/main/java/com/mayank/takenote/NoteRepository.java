package com.mayank.takenote;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository {

    // defining executors in place of Async
    ExecutorService executors= Executors.newSingleThreadExecutor();
    private NoteDao noteDao;
    private LiveData<List<Note>>notes;

    public NoteRepository(Application application)
    {
        NoteDatabase noteDatabase=NoteDatabase.getInstance(application);
        noteDao=noteDatabase.noteDao();
        notes=noteDao.getAllNotes();
    }

    public void insert(Note note)
    {
        executors.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.insert(note);
            }
        });
    }
    public void delete(Note note)
    {

        executors.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.delete(note);
            }
        });
    }
    public void update(Note note)
    {
        executors.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.update(note);
            }
        });
    }
    public LiveData<List<Note>>getAllNotes()
    {
        return notes;
    }

}
