package com.kushnir.elfc.pojo;

import android.net.Uri;
import android.view.View;

public class CardsListItem {

    private final String word;
    private final String transcription;
    private final Uri imageUri;
    private final View.OnClickListener listener;

    public CardsListItem(String word,
                         String transcription,
                         Uri imageUri,
                         View.OnClickListener listener) {
        this.word = word;
        this.transcription = transcription;
        this.imageUri = imageUri;
        this.listener = listener;
    }

    public String getWord() {
        return word;
    }

    public String getTranscription() {
        return transcription;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public View.OnClickListener getListener() {
        return listener;
    }
}
