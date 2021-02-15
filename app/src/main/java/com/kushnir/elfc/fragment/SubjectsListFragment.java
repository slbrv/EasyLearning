package com.kushnir.elfc.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kushnir.elfc.R;
import com.kushnir.elfc.adapter.SubjectsListAdapter;
import com.kushnir.elfc.pojo.SubjectsListItem;

import java.util.ArrayList;

public class SubjectsListFragment extends Fragment {

    private RecyclerView recyclerView;
    private Button addButton;
    private ArrayList<SubjectsListItem> 

    public SubjectsListFragment() {
        super(R.layout.fragment_subjects_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addButton = view.findViewById(R.id.add_subject_button);
        recyclerView = view.findViewById(R.id.subjects_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new SubjectsListAdapter(getContext(), ));
    }
}
