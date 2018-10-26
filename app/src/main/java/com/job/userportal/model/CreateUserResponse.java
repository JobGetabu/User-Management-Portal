package com.job.userportal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Job on Friday : 10/26/2018.
 */
public class CreateUserResponse {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("job")
    @Expose
    private String job;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    /**
     * No args constructor for use in serialization
     *
     */
    public CreateUserResponse() {
    }

    /**
     *
     * @param id
     * @param createdAt
     * @param name
     * @param job
     */
    public CreateUserResponse(String name, String job, String id, String createdAt) {
        super();
        this.name = name;
        this.job = job;
        this.id = id;
        this.createdAt = createdAt;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
