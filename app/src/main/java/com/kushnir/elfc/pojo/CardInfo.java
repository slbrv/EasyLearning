package com.kushnir.elfc.pojo;

public class CardInfo {

    private String word;
    private String transcription;
    private String imageUri;

    public CardInfo() {
        this.word = "";
        this.transcription = "";
        this.imageUri = "";
    }

    public CardInfo(String word, String transcription, String imageUri) {
        this.word = word;
        this.transcription = transcription;
        this.imageUri = imageUri;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getWord() {
        return word;
    }

    public String getTranscription() {
        return transcription;
    }

    public String getImageUri() {
        return imageUri;
    }
}
