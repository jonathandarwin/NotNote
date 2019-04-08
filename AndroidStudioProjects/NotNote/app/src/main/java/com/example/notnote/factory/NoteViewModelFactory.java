package com.example.notnote.factory;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.notnote.viewmodel.NoteViewModel;

public class NoteViewModelFactory implements ViewModelProvider.Factory {

    Application application;

    public NoteViewModelFactory(Application application){
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NoteViewModel(application);
    }
}
