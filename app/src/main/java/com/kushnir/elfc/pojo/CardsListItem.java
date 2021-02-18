package com.kushnir.elfc.pojo;

import android.net.Uri;
import android.view.View;

public class CardsListItem {

    private String word;
    private String transcription;
    private Uri imageUri;
    private View.OnClickListener listener;

    public CardsListItem() {

    }

    public CardsListItem(CardsListItem item) {
        this.word = item.word;
        this.transcription = item.transcription;
        this.imageUri = item.imageUri;
        this.listener = item.listener;
    }


    public void setWord(String word) {
        this.word = word;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public void setImageUri(Uri uri) {
        this.imageUri = uri;
    }

    public void setListener(View.OnClickListener listener) {
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
