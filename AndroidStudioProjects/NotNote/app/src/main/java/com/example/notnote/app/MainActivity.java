package com.example.notnote.app;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.notnote.R;
import com.example.notnote.adapter.NoteAdapter;
import com.example.notnote.common.BaseActivity;
import com.example.notnote.databinding.ActivityMainBinding;
import com.example.notnote.model.Note;
import com.example.notnote.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    NoteAdapter adapter;
    List<Note> listNote;

    public MainActivity(){
        super(MainViewModel.class, R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAdapter();
        setListener();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        String category = "";
        switch(menuItem.getItemId()){
            case R.id.menuFood:
                category = "food";
                break;
            case R.id.menuTodo:
                category = "todo";
                break;
            case R.id.menuSecret:
                category = "secret";
                break;
        }
        getNoteByCategory(category);
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v.equals(getBinding().fabAdd)){
            gotoInsertNoteIntent(new Note());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        listNote.clear();
        adapter.notifyDataSetChanged();
        getNoteByCategory("food");
    }

    private void setListener()
    {
        getBinding().fabAdd.setOnClickListener(this);
        getBinding().bnHome.setOnNavigationItemSelectedListener(this);
    }

    private void initAdapter(){
        listNote = new ArrayList<>();
        adapter = new NoteAdapter(this, listNote);
        getBinding().recyclerView.setHasFixedSize(true);
        getBinding().recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getBinding().recyclerView.setAdapter(adapter);
    }

    private void gotoInsertNoteIntent(Note note)
    {
        Intent intent = new Intent(this, InsertUpdateNoteActivity.class);
        intent.putExtra("type", "INSERT");
        intent.putExtra("note", note);
        startActivity(intent);
    }

    private void gotoUpdateNoteIntent(Note note)
    {
        Intent intent = new Intent(this, InsertUpdateNoteActivity.class);
        intent.putExtra("type", "UPDATE");
        intent.putExtra("note", note);
        startActivity(intent);
    }

    private void getNoteByCategory(String category){
        getViewModel().getByCategory(category).observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                listNote.clear();
                if(notes.size() > 0){
                    listNote.addAll(notes);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void editNote(Note note){
        gotoUpdateNoteIntent(note);
    }

    public void deleteNote(final Note note, final int i){
        AlertDialog.Builder dialog = createDialogConfirmation("Delete \"" + note.getTitle() + "\"", "Are you sure?");

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getViewModel().delete(note.getNoteId()).observe(MainActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if(aBoolean){
                            Toast.makeText(getApplicationContext(), "DELETE SUCCESS", Toast.LENGTH_SHORT).show();
                            listNote.remove(i);
                            adapter.notifyItemRemoved(i);
                            adapter.notifyItemRangeChanged(i, 1);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Error. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        dialog.setNegativeButton("No", null);
        dialog.show();
    }
}
