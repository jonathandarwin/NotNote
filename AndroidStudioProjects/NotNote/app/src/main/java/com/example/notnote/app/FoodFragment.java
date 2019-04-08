package com.example.notnote.app;


import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.notnote.R;
import com.example.notnote.adapter.NoteAdapter;
import com.example.notnote.common.CustomCalendar;
import com.example.notnote.databinding.FragmentFoodBinding;
import com.example.notnote.factory.NoteViewModelFactory;
import com.example.notnote.model.Note;
import com.example.notnote.viewmodel.NoteViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FoodFragment extends Fragment {

    FragmentFoodBinding binding;
    NoteAdapter adapter;
    List<Note> listNote;
    NoteViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_food, container, false);
        listNote = new ArrayList<>();
        //insertData();
        adapter = new NoteAdapter(getContext(), this, listNote);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this, new NoteViewModelFactory(getActivity().getApplication())).get(NoteViewModel.class);
        loadData();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void insertData()
    {
        for(int i=1; i<10; i++){
            Note note = new Note();
            note.setTitle("Ini Title " + i);
            note.setDescription("Ini Description Ini Description Ini Description Ini Description" + i);
            note.setCategory("food");
            note.setAuditedTime(CustomCalendar.GetCurrentDate());
            note.setAuditedActivity("I");

            listNote.add(note);
        }
    }

    private void loadData()
    {
        listNote.clear();
        binding.loading.setVisibility(View.VISIBLE);
        binding.noData.setVisibility(View.GONE);
        viewModel.getByCategory("food").observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                if(notes.size() == 0){
                    binding.noData.setVisibility(View.VISIBLE);
                }
                binding.loading.setVisibility(View.GONE);
                listNote.clear();
                listNote.addAll(notes);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void editNote(Note note){
        gotoInsertUpdateNoteIntent(note);
    }

    public void deleteNote(final Note note, final int i){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete \"" + note.getTitle() + "\"");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                viewModel.delete(note.getNoteId()).observe(FoodFragment.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if(aBoolean){
                            Toast.makeText(getContext(), "DELETE SUCCESS", Toast.LENGTH_SHORT).show();
                            listNote.remove(i);
                            adapter.notifyItemRemoved(i);
                            adapter.notifyItemRangeChanged(i, 1);
                        }
                        else{
                            Toast.makeText(getContext(), "Error. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void gotoInsertUpdateNoteIntent(Note note)
    {
        Intent intent = new Intent(getContext(), InsertUpdateNoteActivity.class);
        intent.putExtra("type", "UPDATE");
        intent.putExtra("note", note);
        startActivity(intent);
    }
}
