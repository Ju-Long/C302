package com.myapplicationdev.android.c302_magic;

public class Colour {

    private int colour_id;
    private String color_name;

    public Colour(int colour_id, String color_name) {
        this.colour_id = colour_id;
        this.color_name = color_name;
    }

    public int getColour_id() {
        return colour_id;
    }

    public String getColor_name() {
        return color_name;
    }
}
