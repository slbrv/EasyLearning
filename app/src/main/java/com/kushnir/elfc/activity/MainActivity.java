package com.kushnir.elfc.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.kushnir.elfc.R;
import com.kushnir.elfc.adapter.item.LangListItem;

public class MainActivity extends AppCompatActivity {

    private Button toTeacherModeButton;
    private Button toLearningModeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toTeacherModeButton = findViewById(R.id.teacher_mode_button);
        toLearningModeButton = findViewById(R.id.learning_mode_button);

        toTeacherModeButton.setOnClickListener(this::toTeacherMode);
        toLearningModeButton.setOnClickListener(this::toLearningMode);
    }

    protected void toTeacherMode(View v) {
        Intent intent = new Intent(this, LangListActivity.class);
        intent.putExtra("mode", 0);
        startActivity(intent);
    }

    protected void toLearningMode(View v) {
        Intent intent = new Intent(this, LangListActivity.class);
        intent.putExtra("mode", 1);
        startActivity(intent);
    }
}