package com.example.guicompanydb.models;

import java.util.ArrayList;
import java.util.Date;

public class Person{
    private String first_name;
    private String last_name;
    private String gender;
    private Date date_of_birth;
    private String email;
    private String street;
    private int streetNum;
    private int postal_code;
    private String city;
    private ArrayList<Project> projects;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth() {
        this.date_of_birth = date_of_birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(int streetNum) {
        this.streetNum = streetNum;
    }

    public int getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(int postal_code) {
        this.postal_code = postal_code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }

    public Person(String first_name, String last_name, String gender, Date date_of_birth, String email, String street, int streetNum, int postal_code, String city, ArrayList<Project> projects) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.date_of_birth = date_of_birth;
        this.email = email;
        this.street = street;
        this.streetNum = streetNum;
        this.postal_code = postal_code;
        this.city = city;
        this.projects = projects;
    }

    @Override
    public String toString(){
        String gsonReturn= first_name + ", " + last_name + ", " + date_of_birth + ", " + street + ", " + streetNum + ", " + postal_code + ", " +
                city + "\n |";

        for(Project project : this.projects){
            gsonReturn += project.getDescription() + "|";
        }

        return gsonReturn;
    }


}
