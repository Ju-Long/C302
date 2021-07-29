package com.myapplicationdev.android.firebaseinventoryapp;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.ArrayList;

public class Phone implements Serializable {

    private String name;
    private double cost;
    private ArrayList<String> options;

    public Phone() {
    }

    public Phone(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public Phone(String name, double cost, ArrayList<String> options) {
        this.name = name;
        this.cost = cost;
        this.options = options;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public ArrayList<String> getOptions() {
        return options;
    }
}
