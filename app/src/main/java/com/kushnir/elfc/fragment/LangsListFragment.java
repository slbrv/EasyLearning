package com.kushnir.elfc.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kushnir.elfc.R;
import com.kushnir.elfc.adapter.LangListAdapter;
import com.kushnir.elfc.pojo.LangListItem;

import java.util.ArrayList;

public class LangsListFragment extends Fragment {

    private RecyclerView langRecyclerView;
    private Button addButton;

    public LangsListFragment() {
        super(R.layout.fragment_langs_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<LangListItem> langsList = new ArrayList<>();
        LangListAdapter adapter = new LangListAdapter(getContext(), langsList);

        addButton = view.findViewById(R.id.add_subject_button);
        langRecyclerView = view.findViewById(R.id.subjects_recycler_view);
        langRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        langRecyclerView.setAdapter(adapter);

        MutableLiveData<String> lang = new MutableLiveData<>();
        lang.observe(this, s -> langsList.add(new LangListItem(s, v -> {
            Log.i("ELFC", "Lang: " + s);
        })));

        addButton.setOnClickListener(v -> {
            AddLangDialogFragment dialog = new AddLangDialogFragment(lang);
            dialog.show(getFragmentManager(), "dialog_add_lang");
        });
    }
}
