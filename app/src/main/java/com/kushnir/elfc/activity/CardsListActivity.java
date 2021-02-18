package com.kushnir.elfc.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kushnir.elfc.R;
import com.kushnir.elfc.adapter.CardsListAdapter;
import com.kushnir.elfc.fragment.AddCardDialogFragment;
import com.kushnir.elfc.pojo.CardsListItem;

import java.util.ArrayList;

public class CardsListActivity extends AppCompatActivity {

    public static class RawCard {
        public String word;
        public String transcription;
    }

    private static final int RESULT_LOAD_IMG = 1;

    private Button addButton;
    private RecyclerView cardsRecyclerView;
    private TextView subjectNameTextView;

    private ArrayList<CardsListItem> cards;

    private MutableLiveData<Uri> imageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_list);

        Intent intent = getIntent();
        ActionBar bar = getSupportActionBar();
        bar.setTitle(intent.getStringExtra("subject"));
        bar.setDisplayHomeAsUpEnabled(true);

        addButton = findViewById(R.id.add_card_button);
        cardsRecyclerView = findViewById(R.id.cards_recycler_view);

        cards = new ArrayList<>();

        imageUri = new MutableLiveData<>();

        //
        // Cards loading from DB
        //
        CardsListAdapter adapter = new CardsListAdapter(this, cards);
        cardsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cardsRecyclerView.setAdapter(adapter);

        CardsListItem item = new CardsListItem();

        MutableLiveData<RawCard> card = new MutableLiveData<>();
        card.observe(this, c -> {
            if(c.word.isEmpty()) {
                makeToast(getResources().getString(R.string.empty_card_name_input_toast));
            } else if(c.transcription.isEmpty()) {
                makeToast(getResources().getString(R.string.empty_card_transcription_input_toast));
            } else {
                item.setWord(c.word);
                item.setTranscription(c.transcription);
                Log.i("APP", "Set word: " + c.word);

                Intent photoIntent = new Intent(Intent.ACTION_PICK);
                photoIntent.setType("image/*");
                startActivityForResult(photoIntent, RESULT_LOAD_IMG);
            }
        });

        imageUri.observe(this, uri -> {
            if(imageUri == null) {
                makeToast(getResources().getString(R.string.empty_card_image_input_toast));
            } else {
                item.setImageUri(uri);
                item.setListener(v -> {
                    Log.i("APP", "Word: " + item.getWord());
                });
                Log.i("APP", "OK");
                CardsListItem temp = new CardsListItem(item);
                cards.add(temp);
                adapter.setData(cards);
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
                    final Uri selected = data.getData();
                    imageUri.postValue(selected);
                    break;
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
