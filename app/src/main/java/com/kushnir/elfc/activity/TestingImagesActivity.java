package com.kushnir.elfc.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kushnir.elfc.R;
import com.kushnir.elfc.data.CardsRepository;
import com.kushnir.elfc.pojo.CardInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TestingImagesActivity extends AppCompatActivity {

    private ImageView fstImageButton;
    private ImageView sndImageButton;
    private ImageView trdImageButton;

    private TextView cardWordTextView;
    private TextView cardTransTextView;

    private ArrayList<CardInfo> cards;
    private int currentCard;
    private int correctAnswers;

    private ArrayList<String> activeWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_images);

        Intent intent = getIntent();
        String lang = intent.getStringExtra("lang");
        String subject = intent.getStringExtra("subject");

        ActionBar bar = getSupportActionBar();
        bar.setTitle(subject);

        this.fstImageButton = findViewById(R.id.testing_image_view_1);
        this.sndImageButton = findViewById(R.id.testing_image_view_2);
        this.trdImageButton = findViewById(R.id.testing_image_view_3);

        this.cardWordTextView = findViewById(R.id.testing_card_word_text_view);
        this.cardTransTextView = findViewById(R.id.testing_card_trans_text_view);

        this.fstImageButton.setOnClickListener(v -> onAnswer(activeWords.get(0)));
        this.sndImageButton.setOnClickListener(v -> onAnswer(activeWords.get(1)));
        this.trdImageButton.setOnClickListener(v -> onAnswer(activeWords.get(2)));

        cards = loadCards(lang, subject);
        this.currentCard = 0;
        this.correctAnswers = 0;
        this.activeWords = new ArrayList<>(3);
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

        int[] nums = new int[2];
        Random random = new Random();
        do {
            nums[0] = random.nextInt(cards.size());
            nums[1] = random.nextInt(cards.size());
        } while (currentCard == nums[0] || currentCard == nums[1] || nums[0] == nums[1]);

        activeWords.clear();
        activeWords.add(card.getWord());
        activeWords.add(cards.get(nums[0]).getWord());
        activeWords.add(cards.get(nums[1]).getWord());
        Collections.shuffle(activeWords);

        cardWordTextView.setText(card.getWord());
        cardTransTextView.setText(card.getTranscription());

        fstImageButton.setImageBitmap(repo.getImage(card.getImagePath()));
        sndImageButton.setImageBitmap(repo.getImage(cards.get(nums[0]).getImagePath()));
        trdImageButton.setImageBitmap(repo.getImage(cards.get(nums[1]).getImagePath()));

        repo.close();
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