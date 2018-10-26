package com.job.userportal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Job on Friday : 10/26/2018.
 */
public class CreateUser {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("job")
    @Expose
    private String job;

    /**
     * No args constructor for use in serialization
     *
     */
    public CreateUser() {
    }

    /**
     *
     * @param name
     * @param job
     */
    public CreateUser(String name, String job) {
        super();
        this.name = name;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
