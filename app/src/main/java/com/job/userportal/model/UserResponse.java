package com.job.userportal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Job on Friday : 10/26/2018.
 */

public class UserResponse {

    @SerializedName("data")
    @Expose
    private UserData data;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserResponse() {
    }

    /**
     *
     * @param data
     */
    public UserResponse(UserData data) {
        super();
        this.data = data;
    }

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "data=" + data +
                "id=" + data.getId() +
                ", firstName='" + data.getFirstName() + '\'' +
                ", lastName='" + data.getLastName() + '\'' +
                ", avatar='" + data.getAvatar() + '\'' +
                '}';
    }
}
