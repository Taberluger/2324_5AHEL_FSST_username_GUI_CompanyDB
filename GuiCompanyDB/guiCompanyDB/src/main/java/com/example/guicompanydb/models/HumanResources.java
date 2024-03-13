package com.example.guicompanydb.models;

import java.util.ArrayList;

public class HumanResources {
    private ArrayList<Person> people;

    public ArrayList<Person> getPeople() {
        return people;
    }

    public HumanResources(ArrayList<Person> people) {
        this.people = people;
    }
}
