package com.kushnir.elfc.pojo;

public class CardInfo {

    private String word;
    private String transcription;
    // lang_subject_word
    private String imagePath;

    public CardInfo() {
        this.word = "";
        this.transcription = "";
        this.imagePath = "null";
    }

    public CardInfo(String word, String transcription, String imagePath) {
        this.word = word;
        this.transcription = transcription;
        this.imagePath = imagePath;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getWord() {
        return word;
    }

    public String getTranscription() {
        return transcription;
    }

    public String getImagePath() {
        return imagePath;
    }
}
