package com.kushnir.elfc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
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

        Intent intent = getIntent();

        ActionBar bar = getSupportActionBar();
        bar.setTitle(intent.getStringExtra("lang"));
        bar.setDisplayHomeAsUpEnabled(true);

        subjects = new ArrayList<>();

        // Загрузка из бд

        MutableLiveData<String> subject = new MutableLiveData<>();
        subject.observe(this, s -> {
            if (s.isEmpty()) {
                Toast.makeText(
                        this,
                        getResources().getText(R.string.empty_subject_input_toast),
                        Toast.LENGTH_LONG).show();
            } else {
                subjects.add(new SubjectsListItem(s, 0, v -> {
                    Log.i("App", "Subject: " + s);
                    Intent toCardsIntent = new Intent(this, CardsListActivity.class);
                    toCardsIntent.putExtra("subject", s);
                    startActivity(toCardsIntent);
                }));
            }
        });

        addButton = findViewById(R.id.add_subject_button);
        addButton.setOnClickListener(v -> {
            AddSubjectDialogFragment dialog = new AddSubjectDialogFragment(subject);
            dialog.show(getSupportFragmentManager(), "dialog_add_subject");
        });

        recyclerView = findViewById(R.id.subjects_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SubjectsListAdapter(this, subjects));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}