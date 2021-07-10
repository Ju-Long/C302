package com.myapplicationdev.android.c302_magic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CardAdapter extends ArrayAdapter<Card> {

    Context context;
    ArrayList<Card> cards;
    int resources;
    TextView card_name, card_price;

    public CardAdapter(Context context, int resource, ArrayList<Card> cards) {
        super(context, resource, cards);
        this.context = context;
        this.resources = resource;
        this.cards = cards;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View card_row = inflater.inflate(resources, parent, false);
        card_name = card_row.findViewById(R.id.card_name_textview);
        card_price = card_row.findViewById(R.id.card_price_textView);

        card_name.setText(cards.get(position).getCard_name());
        card_price.setText(cards.get(position).getPrice());
        return card_row;
    }
}
