package com.myapplicationdev.android.c302_magic;

public class Card {

    private int card_id;
    private String card_name;
    private int colour_id;
    private int type_id;
    private double price;
    private int quantity;

    public Card(int card_id, String card_name, int colour_id, int type_id, double price, int quantity) {
        this.card_id = card_id;
        this.card_name = card_name;
        this.colour_id = colour_id;
        this.type_id = type_id;
        this.price = price;
        this.quantity = quantity;
    }

    public int getCard_id() {
        return card_id;
    }

    public String getCard_name() {
        return card_name;
    }

    public int getColour_id() {
        return colour_id;
    }

    public int getType_id() {
        return type_id;
    }

    public String getPrice() {
        return String.format("%.2f", price);
    }

    public int getQuantity() {
        return quantity;
    }
}
