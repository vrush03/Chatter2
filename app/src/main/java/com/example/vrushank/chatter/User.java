package com.example.vrushank.chatter;

/**
 * Created by vrushank on 9/11/16.
 */

public class User {
    String username;
    String email;
    String uid;

    public User(String[] otherUsers) {
        this.otherUsers = otherUsers;
    }

    String[] otherUsers;

    public User(String username, String email, String uid) {
        this.username = username;
        this.email = email;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}
