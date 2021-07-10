package com.myapplicationdev.android.c302_magic;

public class Type {

    private int type_id;
    private String type_name;

    public Type(int type_id, String type_name) {
        this.type_id = type_id;
        this.type_name = type_name;
    }

    public int getType_id() {
        return type_id;
    }

    public String getType_name() {
        return type_name;
    }
}
