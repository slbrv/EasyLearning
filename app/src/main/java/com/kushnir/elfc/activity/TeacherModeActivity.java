package com.kushnir.elfc.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

import com.kushnir.elfc.R;
import com.kushnir.elfc.adapter.LangListAdapter;
import com.kushnir.elfc.pojo.LangListItem;

import java.util.ArrayList;

public class TeacherModeActivity extends AppCompatActivity {

    private RecyclerView langRecyclerView;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_mode);


        ArrayList<LangListItem> langsList = new ArrayList<>();
        langsList.add(new LangListItem("\uD83C\uDDEC\uD83C\uDDE7 English", v -> {

        }));
        langsList.add(new LangListItem("\uD83C\uDDE9\uD83C\uDDEA Deutsche", v -> {
            Log.i("ELFC", "Deutsche");
        }));
        langsList.add(new LangListItem("\uD83C\uDDEB\uD83C\uDDF7 Français", v -> {
            Log.i("ELFC", "Français");
        }));

        LangListAdapter adapter = new LangListAdapter(this, langsList);

        langRecyclerView = findViewById(R.id.languages_recycler_view);
        addButton = findViewById(R.id.add_lang_button);

        langRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        langRecyclerView.setAdapter(adapter);

        addButton.setOnClickListener(view -> {
            
        });
    }
}