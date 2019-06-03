package com.example.notnote.common;

import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.example.notnote.R;
import com.example.notnote.factory.ViewModelFactory;

public class BaseActivity<VM extends ViewModel, DataBinding extends ViewDataBinding> extends AppCompatActivity {
    protected Dialog dialog;

    private DataBinding binding;
    private VM viewModel;
    private Class<VM> vm;
    private int layout;

    public BaseActivity(){

    }

    public BaseActivity(Class<VM> vm, int layout){
        this.vm = vm;
        this.layout = layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.binding = DataBindingUtil.setContentView(this, layout);
        this.viewModel = ViewModelProviders.of(this, new ViewModelFactory(this)).get(vm);

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
    }

    protected VM getViewModel(){
        return viewModel;
    }

    protected DataBinding getBinding(){
        return binding;
    }

    protected void gotoIntent(Class classIntent, boolean isFinish){
        Intent intent = new Intent(this, classIntent);
        startActivity(intent);
        if(isFinish){
            finish();
        }
    }

    protected AlertDialog.Builder createDialogConfirmation(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        return builder;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    protected void showLoading()
    {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    protected void dismissLoading()
    {
        dialog.dismiss();
    }
}
