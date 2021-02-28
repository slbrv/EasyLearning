package com.kushnir.elfc.adapter.item;

import android.view.View;

import com.kushnir.elfc.pojo.CardInfo;

public class CardListItem {

    private CardInfo info;
    private View.OnClickListener clickListener;
    private View.OnClickListener delListener;

    public CardListItem() {

    }

    public CardListItem(CardListItem item) {
        this.info = item.info;
        this.clickListener = item.clickListener;
    }

    public CardListItem(CardInfo info,
                        View.OnClickListener clickListener,
                        View.OnClickListener delListener) {
        this.info = info;
        this.clickListener = clickListener;
        this.delListener = delListener;
    }


    public void setInfo(CardInfo info) {
        this.info = info;
    }

    public void setClickListener(View.OnClickListener listener) {
        this.clickListener = listener;
    }

    public void setDelListener(View.OnClickListener listener) {
        this.delListener = listener;
    }

    public CardInfo getInfo() {
        return info;
    }

    public View.OnClickListener getClickListener() {
        return clickListener;
    }

    public View.OnClickListener getDelListener() {
        return delListener;
    }

}
