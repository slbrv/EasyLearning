package com.kushnir.elfc.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kushnir.elfc.R;
import com.kushnir.elfc.adapter.SubjectsListAdapter;
import com.kushnir.elfc.fragment.AddSubjectDialogFragment;
import com.kushnir.elfc.pojo.SubjectsListItem;

import java.util.ArrayList;

public class SubjectsListActivity extends AppCompatActivity {

    private ArrayList<SubjectsListItem> subjects;
    private Button addButton;
    private RecyclerView recyclerView;

    public SubjectsListActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects_list);

        subjects = new ArrayList<>();
        subjects.add(new SubjectsListItem("Food", 5, v -> Log.i("ELFC", "Food")));

        // Загрузка из бд

        MutableLiveData<String> subject = new MutableLiveData<>();
        subject.observe(this, s -> subjects.add(new SubjectsListItem(s, 0, v -> {
            Log.i("App", "Subject: " + s);
        })));

        addButton = findViewById(R.id.add_subject_button);
        addButton.setOnClickListener(v -> {
            AddSubjectDialogFragment dialog = new AddSubjectDialogFragment(subject);
            dialog.show(getSupportFragmentManager(), "dialog_add_subject");
        });

        recyclerView = findViewById(R.id.subjects_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SubjectsListAdapter(this, subjects));
    }
}