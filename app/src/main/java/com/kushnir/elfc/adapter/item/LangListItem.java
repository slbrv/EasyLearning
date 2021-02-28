package com.kushnir.elfc.adapter.item;

import android.view.View;

public class LangListItem {

    private final String langName;
    private int subjectsCount;
    private final View.OnClickListener clickListener;
    private final View.OnClickListener delListener;

    public LangListItem(String langName,
                        int subjectsCount,
                        View.OnClickListener clickListener,
                        View.OnClickListener delListener)
    {
        this.langName = langName;
        this.subjectsCount = subjectsCount;
        this.clickListener = clickListener;
        this.delListener = delListener;
    }

    public void setSubjectsCount(int count) {
        this.subjectsCount = count;
    }

    public String getLangName()
    {
        return langName;
    }

    public int getSubjectsCount() {
        return subjectsCount;
    }

    public View.OnClickListener getClickListener()
    {
        return clickListener;
    }

    public View.OnClickListener getDelListener() {
        return delListener;
    }
}
