package com.kushnir.elfc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kushnir.elfc.R;
import com.kushnir.elfc.pojo.LangsListItem;

import java.util.List;

public class LangListAdapter extends RecyclerView.Adapter<LangListAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<LangsListItem> langs;

    public LangListAdapter(Context context, List<LangsListItem> langs) {
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
        LangsListItem item = langs.get(position);
        holder.langText.setText(item.getLangName());
        holder.langText.setOnClickListener(item.getListener());
    }

    @Override
    public int getItemCount() {
        return langs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView langText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.langText = itemView.findViewById(R.id.lang_text_view);
        }
    }
}
