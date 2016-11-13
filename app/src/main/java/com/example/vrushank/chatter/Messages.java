package com.example.vrushank.chatter;

/**
 * Created by vrushank on 9/11/16.
 */

public class Messages {
    String senderId;
    String recieverId;
    int flag;
    String body;


    public Messages(String senderId, String recieverId, int flag, String body) {
        this.senderId = senderId;
        this.recieverId = recieverId;
        this.flag = flag;

        this.body = body;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getRecieverId() {
        return recieverId;
    }

    public int getFlag() {
        return flag;
    }

    public String getBody() {
        return body;
    }

}
