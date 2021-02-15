package com.kushnir.elfc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kushnir.elfc.R;
import com.kushnir.elfc.pojo.LangListItem;

import java.util.List;

public class LangListAdapter extends RecyclerView.Adapter<LangListAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<LangListItem> langs;

    public LangListAdapter(Context context, List<LangListItem> langs) {
        this.inflater = LayoutInflater.from(context);
        this.langs = langs;
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
        String langText = item.getLangName();
        View.OnClickListener listener = item.getListener();
        holder.langTextView.setText(langText);
        holder.langTextView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return langs.size();
    }

    public void addLang(LangListItem item)
    {
        langs.add(item);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView langTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            langTextView = itemView.findViewById(R.id.subject_text_view);
        }
    }
}
