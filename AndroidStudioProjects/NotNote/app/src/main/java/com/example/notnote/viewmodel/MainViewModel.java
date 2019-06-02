package com.example.notnote.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.notnote.model.Note;
import com.example.notnote.repository.NoteRepository;

import java.util.List;

public class MainViewModel extends ViewModel {

    Context context;
    NoteRepository repo;

    public MainViewModel(Context context){
        this.context = context;
        repo = new NoteRepository(context);
    }

    public LiveData<List<Note>> getByCategory(String category){
        MutableLiveData<List<Note>> result = new MutableLiveData<>();
        result.setValue(repo.getByCategory(category));
        return result;
    }

    public LiveData<Boolean> delete(int IDNote){
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        result.setValue(repo.delete(IDNote));
        return result;
    }
}
