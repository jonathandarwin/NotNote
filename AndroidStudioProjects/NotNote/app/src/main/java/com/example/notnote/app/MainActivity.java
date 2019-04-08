package com.example.notnote.app;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.notnote.R;
import com.example.notnote.databinding.ActivityMainBinding;
import com.example.notnote.model.Note;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setListener();

        getSupportFragmentManager().beginTransaction().replace(R.id.layout_home, new FoodFragment()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = new Fragment();
        switch(menuItem.getItemId()){
            case R.id.menuFood:
                fragment = new FoodFragment();
                break;
            case R.id.menuTodo:
                fragment = new FoodFragment();
                break;
            case R.id.menuSecret:
                fragment = new FoodFragment();
                break;
            case R.id.menuOthers:
                fragment = new FoodFragment();
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.layout_home, fragment).commit();
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v.equals(binding.fabAdd)){
            gotoInsertUpdateNoteIntent(new Note());
        }
    }

    private void setListener()
    {
        binding.fabAdd.setOnClickListener(this);
    }

    private void gotoInsertUpdateNoteIntent(Note note)
    {
        Intent intent = new Intent(this, InsertUpdateNoteActivity.class);
        intent.putExtra("type", "INSERT");
        intent.putExtra("note", note);
        startActivity(intent);
    }
}
