package com.kushnir.elfc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kushnir.elfc.R;
import com.kushnir.elfc.pojo.SubjectListItem;

import java.util.List;

public class SubjectsListAdapter extends RecyclerView.Adapter<SubjectsListAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<SubjectListItem> subjects;
    private final String cardsCountText;

    public SubjectsListAdapter(Context context, List<SubjectListItem> subjects) {
        this.inflater = LayoutInflater.from(context);
        this.subjects = subjects;
        this.cardsCountText = context.getResources().getString(R.string.cards_count);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_subject, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubjectListItem item = subjects.get(position);
        holder.subjectName.setText(item.getSubjectName());
        holder.cardsCountText.setText(cardsCountText + ": " + item.getCardsCount());
        holder.subjectName.setOnClickListener(item.getListener());
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView subjectName;
        public final TextView cardsCountText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.subjectName = itemView.findViewById(R.id.subject_name_text_view);
            this.cardsCountText = itemView.findViewById(R.id.cards_count_text_view);
        }
    }
}
