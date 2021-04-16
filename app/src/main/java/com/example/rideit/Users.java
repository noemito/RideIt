package com.example.rideit;


import android.widget.LinearLayout;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;

@IgnoreExtraProperties
public class Users implements Serializable {

    public String name;
    public String age;
    public String email;

    public Users(){

    }

    public Users(String name, String age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }


}
