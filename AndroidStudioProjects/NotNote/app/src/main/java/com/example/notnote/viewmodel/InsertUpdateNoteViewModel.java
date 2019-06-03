package com.example.notnote.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.widget.Toast;

import com.example.notnote.common.APIHandler;
import com.example.notnote.common.CustomCalendar;
import com.example.notnote.model.Note;
import com.example.notnote.repository.NoteRepository;

import org.json.JSONObject;

import java.util.List;
import java.util.prefs.AbstractPreferences;

public class InsertUpdateNoteViewModel extends ViewModel {

    Context context;
    NoteRepository repo;

    public InsertUpdateNoteViewModel(Context context){
        this.context= context;
        repo = new NoteRepository(context);
    }

    public LiveData<Boolean> insert(Note note){
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        if(checkInput(note)){
            note.setAuditedTime(CustomCalendar.GetCurrentDate());
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
            result.setValue(repo.update(note));
        }
        else{
            result.setValue(false);
        }
        return result;
    }

    private boolean checkInput(Note note){
        if(note.getTitle() == null || note.getTitle().equals("") ||
            note.getDescription() == null || note.getDescription().equals("")){
            Toast.makeText(context, "Please input title and description!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public LiveData<String> getCategory(Note note){
        final MutableLiveData<String> result = new MutableLiveData<>();
        String param = note.getTitle() + " " + note.getDescription();
        repo.getCategory(param, new APIHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                try{
                    result.setValue(response.get("result").toString());
                }
                catch(Exception e){
                    result.setValue("");
                }
            }

            @Override
            public void onError(String error) {
                result.setValue("");
            }
        });

        return result;
    }
}
