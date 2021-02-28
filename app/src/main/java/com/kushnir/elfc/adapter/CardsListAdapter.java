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
import com.kushnir.elfc.data.CardsRepository;
import com.kushnir.elfc.adapter.item.CardListItem;

import java.util.ArrayList;

public class CardsListAdapter extends RecyclerView.Adapter<CardsListAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<CardListItem> cards;
    private Context context;

    public CardsListAdapter(Context context, ArrayList<CardListItem> cards) {
        this.inflater = LayoutInflater.from(context);
        this.cards = cards;
        this.context = context;
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
        CardsRepository db = new CardsRepository(context);
        holder.cardName.setText(item.getInfo().getWord());
        holder.cardTranscription.setText(item.getInfo().getTranscription());
        holder.cardImage.setImageBitmap(db.getImage(item.getInfo().getImagePath()));
        holder.delButton.setOnClickListener(item.getDelListener());
        holder.layout.setOnClickListener(item.getClickListener());
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView cardName;
        public final TextView cardTranscription;
        public final ImageView cardImage;
        public final ImageView delButton;
        public final LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.cardName = itemView.findViewById(R.id.card_list_name_text_view);
            this.cardTranscription = itemView.findViewById(R.id.card_list_transcription_text_view);
            this.cardImage = itemView.findViewById(R.id.card_list_image_view);
            this.delButton = itemView.findViewById(R.id.card_list_delete_button);
            this.layout = itemView.findViewById(R.id.card_list_layout);
        }
    }
}
