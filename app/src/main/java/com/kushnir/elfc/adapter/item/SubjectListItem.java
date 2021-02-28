package com.kushnir.elfc.adapter.item;

import android.view.View;

public class SubjectListItem {

    private final String subjectName;
    private final String lang;
    private int cardsCount;
    private final View.OnClickListener clickListener;
    private final View.OnClickListener delListener;

    public SubjectListItem(String subjectName,
                           String lang,
                           int cardsCount,
                           View.OnClickListener clickListener,
                           View.OnClickListener delListener) {
        this.subjectName = subjectName;
        this.lang = lang;
        this.cardsCount = cardsCount;
        this.clickListener = clickListener;
        this.delListener = delListener;
    }

    public void setCardsCount(int count) {
        this.cardsCount = count;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getLang() {
        return lang;
    }

    public int getCardsCount() {
        return cardsCount;
    }

    public View.OnClickListener getClickListener()
    {
        return clickListener;
    }

    public View.OnClickListener getDelListener() {
        return delListener;
    }
}
