package edu.school21.sockets.models;

import java.sql.Timestamp;

public class message {

    private User sender;
    private Timestamp sendTime;
    private String text;

    public message(User sender, Timestamp sendTime, String text) {
        this.sender = sender;
        this.sendTime = sendTime;
        this.text = text;
    }

    public message(User sender) {
        this.sender = sender;
    }

    public User getSender() {
        return sender;
    }

    public Timestamp getSendTime() {
        return sendTime;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "message{" +
                "sender=" + sender +
                ", sendTime=" + sendTime +
                ", text='" + text + '\'' +
                '}';
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    public void setText(String text) {
        this.text = text;
    }
}
