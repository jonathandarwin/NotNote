package com.example.notnote.app;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.notnote.R;
import com.example.notnote.common.BaseActivity;
import com.example.notnote.databinding.ActivityInsertUpdateNoteBinding;
import com.example.notnote.factory.NoteViewModelFactory;
import com.example.notnote.model.Note;
import com.example.notnote.viewmodel.NoteViewModel;

public class InsertUpdateNoteActivity extends BaseActivity implements View.OnClickListener{

    ActivityInsertUpdateNoteBinding binding;
    Note note;
    String type;
    NoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_insert_update_note);
        getData();
        setListener();
        binding.setViewModel(note);
        viewModel = ViewModelProviders.of(this, new NoteViewModelFactory(getApplication())).get(NoteViewModel.class);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(binding.btnSave)){
            note = binding.getViewModel();
            if(type.equals("INSERT")){
                viewModel.insert(note).observe(InsertUpdateNoteActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        checkResponse(aBoolean);
                    }
                });
            }
            else{
                viewModel.update(note).observe(InsertUpdateNoteActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        checkResponse(aBoolean);
                    }
                });
            }
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
            binding.btnSave.setText("UPDATE");
            binding.title.setText("UPDATE NOTE");
        }
    }

    private void setListener()
    {
        binding.btnSave.setOnClickListener(this);
    }
}
