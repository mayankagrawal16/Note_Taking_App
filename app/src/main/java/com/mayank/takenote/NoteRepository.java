package com.mayank.takenote;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository {

    private NoteDao noteDao;
    LiveData<List<Note>>notes;
    ExecutorService executorService= Executors.newSingleThreadExecutor();

    public NoteRepository(Application application)
    {
        NoteDatabase noteDatabase=NoteDatabase.getInstance(application);
        noteDao=noteDatabase.noteDao();
        notes=noteDao.getAllNotes();
    }
    public void insert(Note note)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.insert(note);
            }
        });
    }
    public void update(Note note)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.update(note);
            }
        });
    }
    public void delete(Note note)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.delete(note);
            }
        });
    }
    public LiveData<List<Note>>getAllNotes()
    {
        return notes;
    }
}
