package com.kushnir.elfc.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kushnir.elfc.R;
import com.kushnir.elfc.adapter.CardsListAdapter;
import com.kushnir.elfc.pojo.CardsListItem;

import java.util.ArrayList;

public class CardsListActivity extends AppCompatActivity {

    private Button addButton;
    private RecyclerView cardsRecyclerView;
    private TextView subjectNameTextView;

    private ArrayList<CardsListItem> cards;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_list);

        addButton = findViewById(R.id.add_card_button);
        cardsRecyclerView = findViewById(R.id.cards_recycler_view);
        subjectNameTextView = findViewById(R.id.subject_name_text_view);

        cards = new ArrayList<>();

        //
        // Cards loading from DB
        //

        cardsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cardsRecyclerView.setAdapter(new CardsListAdapter(this, cards));

        MutableLiveData<CardsListItem> card = new MutableLiveData<>();
        card.observe(this, c -> {
            if(c.getWord().isEmpty()) {
                makeToast(getResources().getString(R.string.empty_card_name_input_toast));
            } else if(c.getTranscription().isEmpty()) {
                makeToast(getResources().getString(R.string.empty_card_transcription_input_toast));
            } else if(c.getImageUri().getPath().isEmpty()) {
                makeToast(getResources().getString(R.string.empty_card_image_input_toast));
            } else {
                cards.add(c);
            }
        });

        addButton.setOnClickListener(v -> {

        });
    }

    private void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
