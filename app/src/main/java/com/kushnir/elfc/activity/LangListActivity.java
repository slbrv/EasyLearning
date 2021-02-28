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
import com.kushnir.elfc.adapter.LangListAdapter;
import com.kushnir.elfc.data.CardsRepository;
import com.kushnir.elfc.fragment.AddLangDialogFragment;
import com.kushnir.elfc.adapter.item.LangListItem;

import java.util.ArrayList;

public class LangListActivity extends AppCompatActivity {

    private Button addButton;
    private RecyclerView langRecyclerView;
    private ArrayList<LangListItem> langs;

    private LangListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_langs_list);

        ActionBar bar = getSupportActionBar();
        bar.setTitle(R.string.teacher_mode);
        bar.setDisplayHomeAsUpEnabled(true);

        CardsRepository db = new CardsRepository(getBaseContext());
        ArrayList<String> langStrings = db.getLangs();
        langs = new ArrayList<>();
        for(String lang : langStrings) {
            LangListItem item = new LangListItem(lang, db.getSubjectCount(lang), v -> {
                Intent intent = new Intent(this, SubjectListActivity.class);
                intent.putExtra("lang", lang);
                startActivity(intent);
            }, v -> {
                for (int i = 0; i < langs.size(); i++) {
                    LangListItem it = langs.get(i);
                    if(it.getLangName().equals(lang)) {
                        CardsRepository repo = new CardsRepository(this);
                        repo.delLang(lang);
                        repo.close();
                        langs.remove(it);
                        adapter.notifyDataSetChanged();
                        Log.i("APP", "Delete lang: " + lang);
                        break;
                    }
                }
            });
            langs.add(item);
        }
        adapter = new LangListAdapter(this, langs);

        addButton = findViewById(R.id.add_lang_button);
        langRecyclerView = findViewById(R.id.langs_recycler_view);
        langRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        langRecyclerView.setAdapter(adapter);

        MutableLiveData<String> langLiveData = new MutableLiveData<>();
        langLiveData.observe(this, lang -> {
            if(lang.isEmpty()) {
                Toast.makeText(
                        this,
                        getResources().getText(R.string.empty_lang_input_toast),
                        Toast.LENGTH_LONG).show();
            }
            else {
                Log.i("APP", "INSERT");
                if(db.insertLang(lang)) {
                    langs.add(new LangListItem(lang, 0, v -> {
                        Intent intent = new Intent(this, SubjectListActivity.class);
                        intent.putExtra("lang", lang);
                        startActivity(intent);
                    }, v -> {
                        for (int i = 0; i < langs.size(); i++) {
                            LangListItem it = langs.get(i);
                            if(it.getLangName().equals(lang)) {
                                CardsRepository repo = new CardsRepository(this);
                                repo.delLang(lang);
                                repo.close();
                                langs.remove(it);
                                adapter.notifyDataSetChanged();
                                Log.i("APP", "Delete lang: " + lang);
                                break;
                            }
                        }
                    }));
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getBaseContext(),
                            R.string.lang_has_already_been_added,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        addButton.setOnClickListener(v -> {
            AddLangDialogFragment dialog = new AddLangDialogFragment(langLiveData);
            dialog.show(getSupportFragmentManager(), "dialog_add_lang");
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        CardsRepository db = new CardsRepository(this);
        for(LangListItem lang : langs) {
            lang.setSubjectsCount(db.getSubjectCount(lang.getLangName()));
        }
        db.close();
        adapter.notifyDataSetChanged();
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