package com.kushnir.elfc.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.MutableLiveData;

import com.kushnir.elfc.R;
import com.kushnir.elfc.pojo.CardInfo;

public class AddCardDialogFragment extends DialogFragment {

    private final MutableLiveData<CardInfo> card;

    public AddCardDialogFragment(MutableLiveData<CardInfo> card) {
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
                CardInfo item = new CardInfo();
                item.setWord(cardWordEdit.getText().toString());
                item.setTranscription(cardTranscriptionEdit.getText().toString());
                card.postValue(item);
            }).
            setNegativeButton(R.string.cancel, (dialog, which) -> {
                dialog.cancel();
            }).show();

        return super.onCreateDialog(savedInstanceState);
    }

    public MutableLiveData<CardInfo> getCard() {
        return card;
    }
}
