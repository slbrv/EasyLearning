package com.kushnir.elfc.pojo;

import android.view.View;

public class SubjectListItem {

    private final String subjectName;
    private final int cardsCount;
    private final View.OnClickListener listener;

    public SubjectListItem(String subjectName, int cardsCount, View.OnClickListener listener) {
        this.subjectName = subjectName;
        this.cardsCount = cardsCount;
        this.listener = listener;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getCardsCount() {
        return cardsCount;
    }

    public View.OnClickListener getListener()
    {
        return listener;
    }
}
