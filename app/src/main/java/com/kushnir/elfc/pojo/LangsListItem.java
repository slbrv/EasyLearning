package com.kushnir.elfc.pojo;

import android.view.View;

public class LangsListItem {

    private final String langName;
    private final View.OnClickListener listener;

    public LangsListItem(String langName, View.OnClickListener listener)
    {
        this.langName = langName;
        this.listener = listener;
    }

    public String getLangName()
    {
        return langName;
    }

    public View.OnClickListener getListener()
    {
        return listener;
    }
}
