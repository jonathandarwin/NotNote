package com.example.notnote.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.notnote.BR;

import java.io.Serializable;

public class Note extends BaseObservable implements Serializable {
    protected int noteId;
    protected String title;
    protected String description;
    protected String category;
    protected String auditedTime;
    protected String auditedActivity;

    @Bindable
    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
        notifyPropertyChanged(BR.noteId);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public String getAuditedTime() {
        return auditedTime;
    }

    public void setAuditedTime(String auditedTime) {
        this.auditedTime = auditedTime;
        notifyPropertyChanged(BR.auditedTime);
    }

    @Bindable
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
        notifyPropertyChanged(BR.category);
    }

    @Bindable
    public String getAuditedActivity() {
        return auditedActivity;
    }

    public void setAuditedActivity(String auditedActivity) {
        this.auditedActivity = auditedActivity;
        notifyPropertyChanged(BR.auditedActivity);
    }
}
