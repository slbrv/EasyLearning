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
import com.kushnir.elfc.pojo.CardsListItem;

import java.util.ArrayList;

public class CardsListAdapter extends RecyclerView.Adapter<CardsListAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<CardsListItem> cards;

    public CardsListAdapter(Context context, ArrayList<CardsListItem> cards) {
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
        CardsListItem item = cards.get(position);
        holder.cardName.setText(item.getWord());
        holder.cardTranscription.setText(item.getTranscription());
        holder.cardImage.setImageURI(item.getImageUri());
        holder.layout.setOnClickListener(item.getListener());
    }

    public void setData(ArrayList<CardsListItem> items) {
        this.cards = items;
        notifyDataSetChanged();
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
