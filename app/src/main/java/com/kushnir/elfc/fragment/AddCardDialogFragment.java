package com.kushnir.elfc.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.MutableLiveData;

import com.kushnir.elfc.R;
import com.kushnir.elfc.activity.CardsListActivity;
import com.kushnir.elfc.pojo.CardsListItem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AddCardDialogFragment extends DialogFragment {



    private final MutableLiveData<CardsListActivity.RawCard> card;

    public AddCardDialogFragment(MutableLiveData<CardsListActivity.RawCard> card) {
        this.card = card;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_add_card, null);

        EditText cardWordEdit = view.findViewById(R.id.dialog_card_word_edit_text);
        EditText cardTranscriptionEdit =
                view.findViewById(R.id.dialog_card_transcription_edit_text);


        builder.setView(view).
            setPositiveButton(R.string.add, (dialog, which) -> {

                CardsListActivity.RawCard item = new CardsListActivity.RawCard();
                item.word = cardWordEdit.getText().toString();
                item.transcription = cardTranscriptionEdit.getText().toString();
                card.postValue(item);
            }).
            setNegativeButton(R.string.cancel, (dialog, which) -> {
                dialog.cancel();
            }).show();

        return super.onCreateDialog(savedInstanceState);
    }

    public MutableLiveData<CardsListActivity.RawCard> getCard() {
        return card;
    }
}
