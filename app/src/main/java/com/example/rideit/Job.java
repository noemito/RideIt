package com.example.rideit;

import android.widget.TextView;

import java.io.Serializable;

public class Job implements Serializable {
    private String userId;
    private String userName;
    private String jobDescription;
    private String userAddress;


    public String getUserName() {
        return userName;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public Job()
    {

    }

    public Job(String userId, String user, String description, String userAddress) {
        this.userId = userId;
        this.userName = user;
        this.jobDescription = description;
        this.userAddress = userAddress;
    }

}
