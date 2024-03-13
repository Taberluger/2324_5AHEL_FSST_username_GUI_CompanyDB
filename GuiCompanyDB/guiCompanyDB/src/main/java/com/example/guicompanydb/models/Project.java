package com.example.guicompanydb.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Project{
    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("workingHours")
    @Expose
    private int workingHours;

    public Project(String description, int workingHours) {
        this.description = description;
        this.workingHours = workingHours;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }
}
