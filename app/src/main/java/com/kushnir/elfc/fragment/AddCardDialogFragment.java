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
import com.kushnir.elfc.pojo.CardsListItem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AddCardDialogFragment extends DialogFragment {

    private static final int RESULT_LOAD_IMG = 1;

    private final MutableLiveData<CardsListItem> card;

    private final MutableLiveData<Uri> imageUri;

    public AddCardDialogFragment(MutableLiveData<CardsListItem> card) {
        this.card = card;
        this.imageUri = new MutableLiveData<>();
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
        ImageView cardImageView = view.findViewById(R.id.dialog_card_image_view);
        cardImageView.setOnClickListener(v -> {
            Intent photoIntent = new Intent(Intent.ACTION_PICK);
            photoIntent.setType("image/*");
            getActivity().startActivityForResult(photoIntent, RESULT_LOAD_IMG);
        });

        imageUri.observe(this, uri -> {
            try {
                final InputStream stream = getContext().getContentResolver().openInputStream(imageUri.getValue());
                final Bitmap bitmap = BitmapFactory.decodeStream(stream);
                cardImageView.setImageBitmap(bitmap);
                stream.close();
            } catch (FileNotFoundException e) {
                Log.e("APP", "Error while loading image");
                Toast.makeText(getContext(),
                        getResources().getString(R.string.something_went_wrong),
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("APP", "Error while closing stream");
                e.printStackTrace();
            }
        });

        builder.setView(view).
            setPositiveButton(R.string.add, (dialog, which) -> {
                Intent photoIntent = new Intent(Intent.ACTION_PICK);
                photoIntent.setType("image/*");
                getActivity().startActivityForResult(photoIntent, RESULT_LOAD_IMG);

                CardsListItem item = new CardsListItem(
                        cardWordEdit.getText().toString(),
                        cardTranscriptionEdit.getText().toString(),
                        imageUri.getValue());
                card.postValue(item);
            }).
            setNegativeButton(R.string.cancel, (dialog, which) -> {
                dialog.cancel();
            }).show();

        return super.onCreateDialog(savedInstanceState);
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

    public MutableLiveData<CardsListItem> getCard() {
        return card;
    }
}
