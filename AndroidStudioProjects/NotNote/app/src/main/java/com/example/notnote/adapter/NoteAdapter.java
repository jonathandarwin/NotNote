package com.example.notnote.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.notnote.R;
import com.example.notnote.app.FoodFragment;
import com.example.notnote.common.CustomCalendar;
import com.example.notnote.databinding.ListNoteItemBinding;
import com.example.notnote.model.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    Context context;
    Fragment fragment;
    List<Note> listNote;

    public NoteAdapter(Context context, Fragment fragment, List<Note> listNote){
        this.context = context;
        this.fragment = fragment;
        this.listNote = listNote;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ListNoteItemBinding binding;
        public ViewHolder(ListNoteItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ListNoteItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.list_note_item, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final Note note = listNote.get(i);
        note.setAuditedTime(CustomCalendar.ConvertDateToView(note.getAuditedTime()));
        viewHolder.binding.setViewModel(note);

        viewHolder.binding.header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.binding.expandable.isExpanded()){
                    viewHolder.binding.expandable.collapse(true);
                }
                else {
                    viewHolder.binding.expandable.expand(true);
                }
            }
        });

        viewHolder.binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FoodFragment) fragment).editNote(note);
            }
        });

        viewHolder.binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FoodFragment) fragment).deleteNote(note, i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNote.size();
    }
}
