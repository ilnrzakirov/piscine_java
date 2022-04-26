package edu.models;

import java.sql.Timestamp;
import java.util.Objects;

public class Message {

    private Long id;
    private User author;
    private Chatroom room;
    private String text;
    private Timestamp dateTime;

    public Message(Long id, User author, Chatroom room, String text, Timestamp dateTime) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id && Objects.equals(author, message.author) && Objects.equals(room, message.room) && Objects.equals(text, message.text) && Objects.equals(dateTime, message.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, room, text, dateTime);
    }

    @Override
    public String toString() {
        return "Message{" +
                "\nid=" + id +
                ", \nauthor=" + author +
                ", \nroom=" + room +
                ", \ntext='" + text + '\'' +
                ", \ndateTime=" + dateTime +
                '}';
    }

    public long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public Chatroom getRoom() {
        return room;
    }

    public String getText() {
        return text;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
