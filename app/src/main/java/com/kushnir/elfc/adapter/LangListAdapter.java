package com.kushnir.elfc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kushnir.elfc.R;
import com.kushnir.elfc.adapter.item.LangListItem;

import java.util.List;

public class LangListAdapter extends RecyclerView.Adapter<LangListAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<LangListItem> langs;
    private final String subjectsCountText;

    public LangListAdapter(Context context, List<LangListItem> langs) {
        this.inflater = LayoutInflater.from(context);
        this.langs = langs;
        this.subjectsCountText = context.getResources().getString(R.string.subjects_count);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_lang, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LangListItem item = langs.get(position);
        holder.langText.setText(item.getLangName());
        holder.subjectsCountText.setText(subjectsCountText + ": " + item.getSubjectsCount());
        holder.delButton.setOnClickListener(item.getDelListener());
        holder.layout.setOnClickListener(item.getClickListener());
    }

    @Override
    public int getItemCount() {
        return langs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView langText;
        public final TextView subjectsCountText;
        public final ImageView delButton;
        public final LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.langText = itemView.findViewById(R.id.lang_text_view);
            this.subjectsCountText = itemView.findViewById(R.id.subjects_count_text_view);
            this.delButton = itemView.findViewById(R.id.lang_list_delete_button);
            this.layout = itemView.findViewById(R.id.lang_list_layout);
        }
    }
}
