package com.example.notnote.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.widget.Toast;

import com.example.notnote.common.CustomCalendar;
import com.example.notnote.model.Note;
import com.example.notnote.repository.NoteRepository;

import java.util.List;
import java.util.prefs.AbstractPreferences;

public class NoteViewModel extends ViewModel {

    Application application;

    public NoteViewModel(Application application){
        this.application = application;
    }

    public LiveData<List<Note>> getByCategory(String category){
        MutableLiveData<List<Note>> result = new MutableLiveData<>();
        NoteRepository repo = new NoteRepository(application);
        result.setValue(repo.getByCategory(category));
        return result;
    }

    public LiveData<Boolean> insert(Note note){
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        if(checkInput(note)){
            note.setAuditedTime(CustomCalendar.GetCurrentDate());
            // INI NANTI DIHAPUS
            note.setCategory("food");
            NoteRepository repo = new NoteRepository(application);
            result.setValue(repo.insert(note));
        }
        else{
            result.setValue(false);
        }
        return result;
    }

    public LiveData<Boolean> update(Note note){
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        if(checkInput(note)){
            note.setAuditedTime(CustomCalendar.GetCurrentDate());
            // INI NANTI DIHAPUS
            note.setCategory("food");
            NoteRepository repo = new NoteRepository(application);
            result.setValue(repo.update(note));
        }
        else{
            result.setValue(false);
        }
        return result;
    }

    public LiveData<Boolean> delete(int IDNote){
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        NoteRepository repo = new NoteRepository(application);
        result.setValue(repo.delete(IDNote));
        return result;
    }

    private boolean checkInput(Note note){
        if(note.getTitle() == null || note.getTitle().equals("") ||
            note.getDescription() == null || note.getDescription().equals("")){
            Toast.makeText(application, "Please input title and description!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
