package com.kushnir.elfc.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kushnir.elfc.R;
import com.kushnir.elfc.data.CardsRepository;
import com.kushnir.elfc.pojo.CardListItem;

import java.util.ArrayList;

public class CardsListAdapter extends RecyclerView.Adapter<CardsListAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<CardListItem> cards;

    public CardsListAdapter(Context context, ArrayList<CardListItem> cards) {
        this.inflater = LayoutInflater.from(context);
        this.cards = cards;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardListItem item = cards.get(position);
        holder.cardName.setText(item.getInfo().getWord());
        holder.cardTranscription.setText(item.getInfo().getTranscription());
        holder.cardImage.setImageBitmap(item.getInfo().getImage());
        holder.layout.setOnClickListener(item.getListener());
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView cardName;
        public final TextView cardTranscription;
        public final ImageView cardImage;
        public final LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.cardName = itemView.findViewById(R.id.card_list_name_text_view);
            this.cardTranscription = itemView.findViewById(R.id.card_list_transcription_text_view);
            this.cardImage = itemView.findViewById(R.id.card_list_image_view);
            this.layout = itemView.findViewById(R.id.card_list_layout);
        }
    }
}
