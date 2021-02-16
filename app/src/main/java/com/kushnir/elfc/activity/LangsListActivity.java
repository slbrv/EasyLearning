package com.kushnir.elfc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kushnir.elfc.R;
import com.kushnir.elfc.adapter.LangListAdapter;
import com.kushnir.elfc.fragment.AddLangDialogFragment;
import com.kushnir.elfc.pojo.LangsListItem;

import java.util.ArrayList;

public class LangsListActivity extends AppCompatActivity {

    private Button addButton;
    private RecyclerView langRecyclerView;
    private ArrayList<LangsListItem> langs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_langs_list);

        langs = new ArrayList<>();
        LangListAdapter adapter = new LangListAdapter(this, langs);

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
                langs.add(new LangsListItem(s, 0, v -> {
                    Intent intent = new Intent(this, SubjectsListActivity.class);
                    intent.putExtra("lang", s);
                    startActivity(intent);
                }));
            }
        });

        addButton.setOnClickListener(v -> {
            AddLangDialogFragment dialog = new AddLangDialogFragment(lang);
            dialog.show(getSupportFragmentManager(), "dialog_add_lang");
        });
    }
}