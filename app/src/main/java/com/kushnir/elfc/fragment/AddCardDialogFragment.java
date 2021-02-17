package com.kushnir.elfc.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.MutableLiveData;

import com.kushnir.elfc.R;
import com.kushnir.elfc.pojo.CardsListItem;

public class AddCardDialogFragment extends DialogFragment {

    private static int RESULT_LOAD_IMG = 1;

    private final MutableLiveData<String> word;
    private final MutableLiveData<String> transcription;
    private final MutableLiveData<Uri> icon;

    public AddCardDialogFragment(MutableLiveData<String> word,
                                 MutableLiveData<String> transcription,
                                 MutableLiveData<Uri> icon) {
        this.word = word;
        this.transcription = transcription;
        this.icon = icon;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_add_card, null)).
            setPositiveButton(R.string.add, (dialog, which) -> {
                EditText cardWordEdit = getDialog().findViewById(R.id.dialog_card_word_edit_text);
                EditText cardTranscriptionEdit =
                        getDialog().findViewById(R.id.dialog_card_transcription_edit_text);
                ImageView cardImageView = getDialog().findViewById(R.id.dialog_card_image_view);
                cardImageView.setOnClickListener(v -> {
                    Intent photoIntent = new Intent(Intent.ACTION_PICK);
                    photoIntent.setType("image/*");
                    startActivityForResult(photoIntent, RESULT_LOAD_IMG);
                });

//                CardsListItem item = new CardsListItem(
//                        cardWordEdit.getText().toString(),
//                        cardTranscriptionEdit.getText().toString(), );
            });

        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public MutableLiveData<String> getWord() {
        return word;
    }

    public MutableLiveData<String> getTranscription() {
        return transcription;
    }

    public MutableLiveData<Uri> getIcon() {
        return icon;
    }
}
