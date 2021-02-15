package com.kushnir.elfc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kushnir.elfc.pojo.SubjectsListItem;

import java.util.List;

public class SubjectsListAdapter extends RecyclerView.Adapter {

    private final LayoutInflater inflater;
    private final List<SubjectsListItem> subjectsList;

    public SubjectsListAdapter(Context context, List<SubjectsListItem> subjectsList) {
        this.inflater = LayoutInflater.from(context);
        this.subjectsList = subjectsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
