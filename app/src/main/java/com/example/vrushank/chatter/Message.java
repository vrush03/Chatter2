package com.example.vrushank.chatter;

/**
 * Created by vrushank on 9/11/16.
 */

public class Message {
    String message;
    String sender;
    private boolean self;

    public boolean isSelf() {
        return self;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public Message(String message,  String sender, boolean self) {
        this.message = message;
        this.self = self;
        this.sender = sender;
    }


}
