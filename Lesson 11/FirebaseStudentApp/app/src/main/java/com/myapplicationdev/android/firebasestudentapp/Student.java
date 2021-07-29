package com.myapplicationdev.android.firebasestudentapp;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;


public class Student implements Serializable {

    private String id;
    private String name;
    private int age;

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return name;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
