package com.kushnir.elfc.pojo;

import android.view.View;

public class LangListItem {

    private String langName;
    private View.OnClickListener listener;

    public LangListItem(String langName, View.OnClickListener listener)
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
