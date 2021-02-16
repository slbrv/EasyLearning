package com.kushnir.elfc.pojo;

import android.view.View;

public class LangsListItem {

    private final String langName;
    private final int subjectsCount;
    private final View.OnClickListener listener;

    public LangsListItem(String langName, int subjectsCount, View.OnClickListener listener)
    {
        this.langName = langName;
        this.subjectsCount = subjectsCount;
        this.listener = listener;
    }

    public String getLangName()
    {
        return langName;
    }

    public int getSubjectsCount() {
        return subjectsCount;
    }

    public View.OnClickListener getListener()
    {
        return listener;
    }
}
