package com.example.notnote.app;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.notnote.R;
import com.example.notnote.common.BaseActivity;
import com.example.notnote.databinding.ActivityInsertUpdateNoteBinding;
import com.example.notnote.factory.ViewModelFactory;
import com.example.notnote.model.Note;
import com.example.notnote.viewmodel.InsertUpdateNoteViewModel;

public class InsertUpdateNoteActivity extends BaseActivity<InsertUpdateNoteViewModel, ActivityInsertUpdateNoteBinding> implements View.OnClickListener{

    Note note;
    String type;

    public InsertUpdateNoteActivity(){
        super(InsertUpdateNoteViewModel.class, R.layout.activity_insert_update_note);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        setListener();
        getBinding().setViewModel(note);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(getBinding().btnSave)){
            note = getBinding().getViewModel();
            getViewModel().getCategory(note).observe(InsertUpdateNoteActivity.this, new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    if(!s.equals("")){
                        note.setCategory(s);
                        if(type.equals("INSERT")){
                            getViewModel().insert(note).observe(InsertUpdateNoteActivity.this, new Observer<Boolean>() {
                                @Override
                                public void onChanged(@Nullable Boolean aBoolean) {
                                    checkResponse(aBoolean);
                                }
                            });
                        }
                        else{
                            getViewModel().update(note).observe(InsertUpdateNoteActivity.this, new Observer<Boolean>() {
                                @Override
                                public void onChanged(@Nullable Boolean aBoolean) {
                                    checkResponse(aBoolean);
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    private void checkResponse(boolean aBoolean)
    {
        if(aBoolean){
            Toast.makeText(this, type + " SUCCESS", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void getData()
    {
        note = (Note) getIntent().getSerializableExtra("note");
        type = getIntent().getStringExtra("type");
        if(type.equals("UPDATE")){
            getBinding().btnSave.setText("UPDATE");
            getBinding().title.setText("UPDATE NOTE");
        }
    }

    private void setListener()
    {
        getBinding().btnSave.setOnClickListener(this);
    }
}
