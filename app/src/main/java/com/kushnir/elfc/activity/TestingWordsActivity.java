package com.kushnir.elfc.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.kushnir.elfc.R;
import com.kushnir.elfc.data.CardsRepository;
import com.kushnir.elfc.pojo.CardInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TestingWordsActivity extends AppCompatActivity {

    private Button fstWordButton;
    private Button sndWordButton;
    private Button trdWordButton;

    private ImageView cardImageView;

    private ArrayList<CardInfo> cards;
    private int currentCard;
    private int correctAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_words);

        Intent intent = getIntent();
        String lang = intent.getStringExtra("lang");
        String subject = intent.getStringExtra("subject");

        ActionBar bar = getSupportActionBar();
        bar.setTitle(subject);

        this.fstWordButton = findViewById(R.id.first_word_button);
        this.sndWordButton = findViewById(R.id.second_word_button);
        this.trdWordButton = findViewById(R.id.third_word_button);

        this.cardImageView = findViewById(R.id.testing_card_image_view);

        this.fstWordButton.setOnClickListener(v -> onAnswer(fstWordButton.getText().toString()));
        this.sndWordButton.setOnClickListener(v -> onAnswer(sndWordButton.getText().toString()));
        this.trdWordButton.setOnClickListener(v -> onAnswer(trdWordButton.getText().toString()));


        cards = loadCards(lang, subject);
        this.currentCard = 0;
        this.correctAnswers = 0;
        inflateCard();
    }

    @Override
    public void onBackPressed() {
        showGetBackDialog();
    }

    private ArrayList<CardInfo> loadCards(String lang, String subject) {
        CardsRepository repo = new CardsRepository(this);
        ArrayList<CardInfo> loaded = repo.getCards(lang, subject);
        Collections.shuffle(loaded);
        repo.close();
        return loaded;
    }

    private void onAnswer(String answer) {
        if (answer.equals(cards.get(currentCard).getWord()))
            ++correctAnswers;
        ++currentCard;

        if(currentCard < cards.size())
            inflateCard();
        else
            showStatisticsDialog();
    }

    private void inflateCard() {
        CardInfo card = cards.get(currentCard);

        CardsRepository repo = new CardsRepository(this);
        Bitmap img = repo.getImage(card.getImagePath());
        repo.close();

        int[] nums = new int[2];
        Random random = new Random();
        do {
            nums[0] = random.nextInt(cards.size());
            nums[1] = random.nextInt(cards.size());
        } while (currentCard == nums[0] || currentCard == nums[1] || nums[0] == nums[1]);

        ArrayList<String> answers = new ArrayList<>(3);
        answers.add(card.getWord());
        answers.add(cards.get(nums[0]).getWord());
        answers.add(cards.get(nums[1]).getWord());
        Collections.shuffle(answers);

        cardImageView.setImageBitmap(img);
        fstWordButton.setText(answers.get(0));
        sndWordButton.setText(answers.get(1));
        trdWordButton.setText(answers.get(2));
    }

    private void showStatisticsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.warning);
        Resources resources = getResources();
        String cardsNumberText = resources.getString(R.string.number_of_cards) + ": " + cards.size();
        String correctAnswersText =
                resources.getString(R.string.correct_answers) +
                        ": " + correctAnswers + "(" +
                        ((double)correctAnswers / (double)cards.size()) * 100 + "%)";
        String message = cardsNumberText + "\n" + correctAnswersText;
        builder.setMessage(message);
        builder.setPositiveButton(R.string.ok, (d, v) -> finish());
        builder.setOnDismissListener((d) -> finish());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showGetBackDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.warning);
        builder.setMessage(R.string.test_get_back_message);
        builder.setPositiveButton(R.string.ok, (d, v) -> finish());
        builder.setNegativeButton(R.string.cancel, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}