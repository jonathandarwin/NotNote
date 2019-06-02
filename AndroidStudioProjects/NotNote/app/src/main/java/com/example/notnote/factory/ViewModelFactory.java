package com.example.notnote.factory;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.notnote.app.InsertUpdateNoteActivity;
import com.example.notnote.app.MainActivity;
import com.example.notnote.viewmodel.InsertUpdateNoteViewModel;
import com.example.notnote.viewmodel.MainViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    Context context;

    public ViewModelFactory(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(context instanceof MainActivity)
            return (T) new MainViewModel(context);
        if(context instanceof InsertUpdateNoteActivity)
            return (T) new InsertUpdateNoteViewModel(context);
        return null;
    }
}
