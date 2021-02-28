package com.kushnir.elfc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kushnir.elfc.R;
import com.kushnir.elfc.adapter.SubjectsListAdapter;
import com.kushnir.elfc.data.CardsRepository;
import com.kushnir.elfc.fragment.AddSubjectDialogFragment;
import com.kushnir.elfc.adapter.item.SubjectListItem;

import java.util.ArrayList;

public class SubjectListActivity extends AppCompatActivity {

    private ArrayList<SubjectListItem> subjects;
    private Button addButton;
    private RecyclerView recyclerView;

    private SubjectsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects_list);

        Intent intent = getIntent();
        String lang = intent.getStringExtra("lang");

        ActionBar bar = getSupportActionBar();
        bar.setTitle(lang);
        bar.setDisplayHomeAsUpEnabled(true);

        CardsRepository db = new CardsRepository(this);

        ArrayList<String> subjectStrings = db.getSubjects(lang);
        subjects = new ArrayList<>();
        for(String subject : subjectStrings) {
            SubjectListItem item = new SubjectListItem(subject,
                    lang,
                    db.getCardCount(lang, subject), v -> {
                Intent toCardsIntent = new Intent(this, CardListActivity.class);
                toCardsIntent.putExtra("lang", lang);
                toCardsIntent.putExtra("subject", subject);
                startActivity(toCardsIntent);
            }, v -> {
                for (int i = 0; i < subjects.size(); i++) {
                    SubjectListItem it = subjects.get(i);
                    if(it.getSubjectName().equals(subject)) {
                        CardsRepository repo = new CardsRepository(this);
                        repo.delSubject(lang, subject);
                        repo.close();
                        subjects.remove(it);
                        adapter.notifyDataSetChanged();
                        Log.i("APP", "Delete subject: " + subject);
                        break;
                    }
                }
            });
            subjects.add(item);
        }

        MutableLiveData<String> subjectLiveData = new MutableLiveData<>();
        subjectLiveData.observe(this, subject -> {
            if (subject.isEmpty()) {
                Toast.makeText(
                        this,
                        getResources().getText(R.string.empty_subject_input_toast),
                        Toast.LENGTH_LONG).show();
            } else {
                if(db.insertSubject(lang, subject)) {
                    subjects.add(new SubjectListItem(subject, lang,0, v -> {
                        Log.i("App", "Subject: " + subject);
                        Intent toCardsIntent = new Intent(this, CardListActivity.class);
                        toCardsIntent.putExtra("lang", lang);
                        toCardsIntent.putExtra("subject", subject);
                        startActivity(toCardsIntent);
                    }, v -> {
                        for (int i = 0; i < subjects.size(); i++) {
                            SubjectListItem it = subjects.get(i);
                            if(it.getSubjectName().equals(subject)) {
                                CardsRepository repo = new CardsRepository(this);
                                repo.delSubject(lang, subject);
                                repo.close();
                                subjects.remove(it);
                                adapter.notifyDataSetChanged();
                                Log.i("APP", "Delete subject: " + subject);
                                break;
                            }
                        }
                    }));
                } else {
                    Toast.makeText(
                            this,
                            R.string.subject_has_already_been_added,
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        addButton = findViewById(R.id.add_subject_button);
        addButton.setOnClickListener(v -> {
            AddSubjectDialogFragment dialog = new AddSubjectDialogFragment(subjectLiveData);
            dialog.show(getSupportFragmentManager(), "dialog_add_subject");
        });

        adapter = new SubjectsListAdapter(this, subjects);

        recyclerView = findViewById(R.id.subjects_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CardsRepository repo = new CardsRepository(this);
        for(SubjectListItem item : subjects) {
            item.setCardsCount(repo.getCardCount(item.getLang(), item.getSubjectName()));
        }
        adapter.notifyDataSetChanged();
        repo.close();
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