package com.kushnir.elfc.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kushnir.elfc.R;
import com.kushnir.elfc.adapter.SubjectsListAdapter;
import com.kushnir.elfc.adapter.item.SubjectListItem;
import com.kushnir.elfc.data.CardsRepository;
import com.kushnir.elfc.fragment.AddSubjectDialogFragment;

import java.util.ArrayList;

public class SubjectListActivity extends AppCompatActivity {

    private ArrayList<SubjectListItem> subjects;
    private Button addButton;
    private RecyclerView recyclerView;

    private SubjectsListAdapter adapter;

    private ActivityMode mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects_list);

        Intent intent = getIntent();
        String lang = intent.getStringExtra("lang");
        mode = intent.getIntExtra("mode", -1) == 0 ?
                ActivityMode.TEACHER_MODE :
                ActivityMode.STUDENT_MODE;

        ActionBar bar = getSupportActionBar();
        bar.setTitle(lang);
        bar.setDisplayHomeAsUpEnabled(true);

        CardsRepository db = new CardsRepository(this);

        ArrayList<String> subjectStrings = db.getSubjects(lang);
        subjects = new ArrayList<>();
        for(String subject : subjectStrings) {
            int cardCount = db.getCardCount(lang, subject);
            if(mode == ActivityMode.STUDENT_MODE && cardCount <= 0)
                continue;

            SubjectListItem item = new SubjectListItem(subject,
                    lang,
                    cardCount, v -> {
                switch (mode) {
                    case TEACHER_MODE:
                        Intent toCardsIntent = new Intent(this, CardListActivity.class);
                        toCardsIntent.putExtra("lang", lang);
                        toCardsIntent.putExtra("subject", subject);
                        startActivity(toCardsIntent);
                        break;
                    case STUDENT_MODE:
                        onSelectMode(lang, subject);
                        break;
                }
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
                        switch (mode) {
                            case TEACHER_MODE:
                                Intent toCardsIntent = new Intent(this, CardListActivity.class);
                                toCardsIntent.putExtra("lang", lang);
                                toCardsIntent.putExtra("subject", subject);
                                toCardsIntent.putExtra("mode", 0);
                                startActivity(toCardsIntent);
                                break;
                            case STUDENT_MODE:
                                onSelectMode(lang, subject);
                                break;
                        }
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
        switch (mode) {
            case TEACHER_MODE:
                addButton.setOnClickListener(v -> {
                    AddSubjectDialogFragment dialog = new AddSubjectDialogFragment(subjectLiveData);
                    dialog.show(getSupportFragmentManager(), "dialog_add_subject");
                });
                addButton.setVisibility(View.VISIBLE);
                break;
            case STUDENT_MODE:
                addButton.setVisibility(View.GONE);
                break;
        }

        adapter = new SubjectsListAdapter(this, subjects, mode);

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

    public void onSelectMode(String lang, String subject) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Resources res = getResources();
        builder.setTitle(res.getString(R.string.mode));

        String[] mods = {res.getString(R.string.learning),
                         res.getString(R.string.word_mode),
                         res.getString(R.string.image_mode)};
        builder.setItems(mods, (dialog, which) -> {
            switch (which) {
                case 0:
                    // Learning mode
                    Log.i("APP", "To learning mode: lang(" + lang + ") subject(" + subject + ")");
                    Intent intent = new Intent(this, LearningActivity.class);
                    intent.putExtra("lang", lang);
                    intent.putExtra("subject", subject);
                    startActivity(intent);
                    break;
                case 1:
                    // Word mode
                    Log.i("APP", "To word mode: lang(" + lang + ") subject(" + subject + ")");
                    break;
                case 2:
                    // Image mode
                    Log.i("APP", "To image mode: lang(" + lang + ") subject(" + subject + ")");
                    break;
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}