package com.kushnir.elfc.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kushnir.elfc.R;
import com.kushnir.elfc.adapter.CardsListAdapter;
import com.kushnir.elfc.data.CardsRepository;
import com.kushnir.elfc.fragment.AddCardDialogFragment;
import com.kushnir.elfc.pojo.CardInfo;
import com.kushnir.elfc.pojo.CardListItem;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

//TODO Improve this in the future using ItemTouchHelper
public class CardsListActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMG = 1;

    private Button addButton;
    private RecyclerView cardsRecyclerView;
    private TextView subjectNameTextView;

    private ArrayList<CardListItem> cards;

    private String lang;
    private String subject;
    private String word;
    private String tsp;

    private CardsRepository db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_list);

        Intent intent = getIntent();
        lang = intent.getStringExtra("lang");
        subject = intent.getStringExtra("subject");

        ActionBar bar = getSupportActionBar();
        bar.setTitle(subject);
        bar.setDisplayHomeAsUpEnabled(true);

        addButton = findViewById(R.id.add_card_button);
        cardsRecyclerView = findViewById(R.id.cards_recycler_view);

        db = new CardsRepository(this);

        cards = new ArrayList<>();
        ArrayList<CardInfo> cardInfo = db.getCards(lang, subject);
        for(CardInfo info : cardInfo) {
            cards.add(new CardListItem(info, v -> {
                Log.i("APP", "Word: " + info.getWord());
            }));
        }


        CardsListAdapter adapter = new CardsListAdapter(this, cards);
        cardsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cardsRecyclerView.setAdapter(adapter);

        CardListItem item = new CardListItem();

        MutableLiveData<CardInfo> card = new MutableLiveData<>();
        card.observe(this, c -> {
            if(c.getWord().isEmpty()) {
                makeToast(getResources().getString(R.string.empty_card_name_input_toast));
            } else if(c.getTranscription().isEmpty()) {
                makeToast(getResources().getString(R.string.empty_card_transcription_input_toast));
            } else {
                Log.i("APP", "Set word: " + c.getWord());

                Intent photoIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                word = c.getWord();
                tsp = c.getTranscription();
                startActivityForResult(photoIntent, RESULT_LOAD_IMG);
            }
        });

        addButton.setOnClickListener(v -> {
            AddCardDialogFragment dialog = new AddCardDialogFragment(card);
            dialog.show(getSupportFragmentManager(), "dialog_add_card");
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RESULT_LOAD_IMG:
                    final Uri uri = data.getData();

                    try {
                        Bitmap image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        CardsRepository db = new CardsRepository(this);
                        Bitmap scaled = Bitmap.createScaledBitmap(image, 256, 256, false);
                        String path = lang + "_" + subject + "_" + word;
                        if(db.insertImage(path, scaled)) {
                            CardInfo info = new CardInfo(word, tsp, path);
                            CardListItem item = new CardListItem(info, v -> {
                                Log.i("APP", "Word: " + word);
                            });
                            if (db.insertCard(lang, subject, info))
                                cards.add(item);
                            else
                                Toast.makeText(
                                        this,
                                        R.string.word_has_already_been_added,
                                        Toast.LENGTH_LONG).show();
                            break;
                        }
                        else {
                            Log.e("APP", "Word: " + word + " wasn't created (image insert error)");
                        }
                        db.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                default:
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
