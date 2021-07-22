package com.myapplicationdev.android.petboardingapp;


import java.io.Serializable;
import java.util.Date;

public class Pet implements Serializable {

    private String name;
    private int numDays;
    private String petType;
    private Date boardDate;
    private boolean vaccinated;

    public Pet() {

    }

    public Pet(String name, int numDays, String petType, Date boardDate, boolean vaccinated) {
        this.name = name;
        this.numDays = numDays;
        this.petType = petType;
        this.boardDate = boardDate;
        this.vaccinated = vaccinated;
    }

    public String getName() {
        return name;
    }

    public int getNumDays() {
        return numDays;
    }

    public String getPetType() {
        return petType;
    }

    public Date getBoardDate() {
        return boardDate;
    }

    public boolean isVaccinated() {
        return vaccinated;
    }
}
