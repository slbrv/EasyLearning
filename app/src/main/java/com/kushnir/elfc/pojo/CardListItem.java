package com.kushnir.elfc.pojo;

import android.net.Uri;
import android.view.View;

public class CardListItem {

    private CardInfo info;
    private View.OnClickListener listener;

    public CardListItem() {

    }

    public CardListItem(CardListItem item) {
        this.info = item.info;
        this.listener = item.listener;
    }

    public CardListItem(CardInfo info, View.OnClickListener listener) {
        this.info = info;
        this.listener = listener;
    }


    public void setInfo(CardInfo info) {
        this.info = info;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public CardInfo getInfo() {
        return info;
    }

    public View.OnClickListener getListener() {
        return listener;
    }
}
