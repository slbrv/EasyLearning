package com.kushnir.elfc.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kushnir.elfc.R;
import com.kushnir.elfc.data.CardsRepository;
import com.kushnir.elfc.pojo.CardInfo;

import java.util.ArrayList;
import java.util.Collections;

public class LearningActivity extends AppCompatActivity {

    private ImageView cardImageView;
    private TextView cardWordView;
    private TextView cardTscView;

    private Button rememberedButton;
    private Button toRepeatButton;

    private ArrayList<CardInfo> cards;
    private int currentCard;
    private int remCount;
    private int repCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        Intent intent = getIntent();
        String lang = intent.getStringExtra("lang");
        String subject = intent.getStringExtra("subject");

        this.cardImageView = findViewById(R.id.learning_card_image_view);
        this.cardWordView = findViewById(R.id.learning_card_word_text_view);
        this.cardTscView = findViewById(R.id.learning_card_transcription_text_view);

        this.rememberedButton = findViewById(R.id.learned_card_button);
        this.toRepeatButton = findViewById(R.id.to_repeat_card_button);

        this.cards = loadCards(lang, subject);
        this.currentCard = 0;
        this.remCount = 0;
        this.repCount = 0;
        inflateCardToView(currentCard);

        this.rememberedButton.setOnClickListener(v -> {
            cardToRemembered();
        });
        this.toRepeatButton.setOnClickListener(v -> {
            cardToRepeat();
        });
    }

    @Override
    public void onBackPressed() {
        showStatistics();
    }

    private ArrayList<CardInfo> loadCards(String lang, String subject) {
        CardsRepository repo = new CardsRepository(this);
        ArrayList<CardInfo> loaded = repo.getCards(lang, subject);
        Collections.shuffle(loaded);
        repo.close();
        return loaded;
    }

    private void inflateCardToView(int idx) {
        CardsRepository repo = new CardsRepository(this);
        CardInfo card = cards.get(idx);
        cardImageView.setImageBitmap(repo.getImage(card.getImagePath()));
        cardWordView.setText(card.getWord());
        cardTscView.setText(card.getTranscription());
        repo.close();
    }

    private void cardToRemembered() {
        ++remCount;
        ++currentCard;
        if(currentCard >= cards.size())
            showStatistics();
        else
            inflateCardToView(currentCard);
    }

    private void cardToRepeat() {
        CardInfo card = cards.get(currentCard);
        cards.add(card);
        ++repCount;
        inflateCardToView(++currentCard);
    }

    private void showStatistics() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Resources resources = getResources();
        builder.setTitle(resources.getString(R.string.great));
        String learnedWords = resources.getString(R.string.learned_words) + ": " + remCount;
        String repeatedWords = resources.getString(R.string.repetitions_count) + ": " + repCount;
        String message = learnedWords + "\n" + repeatedWords;
        builder.setMessage(message);
        builder.setPositiveButton(resources.getString(R.string.ok), (d, v) -> {
            finish();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}