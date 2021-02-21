package com.kushnir.elfc.pojo;

import android.graphics.Bitmap;

public class CardInfo {

    private String word;
    private String transcription;
    private Bitmap image;

    public CardInfo() {
        this.word = "";
        this.transcription = "";
        this.image = null;
    }

    public CardInfo(String word, String transcription, Bitmap image) {
        this.word = word;
        this.transcription = transcription;
        this.image = image;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getWord() {
        return word;
    }

    public String getTranscription() {
        return transcription;
    }

    public Bitmap getImage() {
        return image;
    }
}
