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
import com.kushnir.elfc.adapter.LangsListAdapter;
import com.kushnir.elfc.data.CardsRepository;
import com.kushnir.elfc.fragment.AddLangDialogFragment;
import com.kushnir.elfc.pojo.LangListItem;

import java.util.ArrayList;

public class LangsListActivity extends AppCompatActivity {

    private Button addButton;
    private RecyclerView langRecyclerView;
    private ArrayList<LangListItem> langs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_langs_list);

        ActionBar bar = getSupportActionBar();
        bar.setTitle(R.string.teacher_mode);
        bar.setDisplayHomeAsUpEnabled(true);

        CardsRepository db = new CardsRepository(getBaseContext());
        db.dropCards();
        db.createCards();
        ArrayList<String> langStrings = db.getLangs();
        langs = new ArrayList<>();
        for(String lang : langStrings) {
            LangListItem item = new LangListItem(lang, db.getSubjectCount(lang), v -> {
                Intent intent = new Intent(this, SubjectsListActivity.class);
                intent.putExtra("lang", lang);
                startActivity(intent);
            });
            langs.add(item);
        }
        LangsListAdapter adapter = new LangsListAdapter(this, langs);

        addButton = findViewById(R.id.add_lang_button);
        langRecyclerView = findViewById(R.id.langs_recycler_view);
        langRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        langRecyclerView.setAdapter(adapter);

        MutableLiveData<String> lang = new MutableLiveData<>();
        lang.observe(this, s -> {
            if(s.isEmpty()) {
                Toast.makeText(
                        this,
                        getResources().getText(R.string.empty_lang_input_toast),
                        Toast.LENGTH_LONG).show();
            }
            else {
                Log.i("APP", "INSERT");
                if(db.insertLang(s)) {
                    langs.add(new LangListItem(s, 0, v -> {
                        Intent intent = new Intent(this, SubjectsListActivity.class);
                        intent.putExtra("lang", s);
                        startActivity(intent);
                    }));
                } else {
                    Toast.makeText(getBaseContext(),
                            R.string.lang_has_already_been_added,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        addButton.setOnClickListener(v -> {
            AddLangDialogFragment dialog = new AddLangDialogFragment(lang);
            dialog.show(getSupportFragmentManager(), "dialog_add_lang");
        });
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