package edu.models;

import java.time.LocalDateTime;

public class Message {

    private long id;
    private User author;
    private Chatroom room;
    private String text;
    private LocalDateTime dateTime;
}
