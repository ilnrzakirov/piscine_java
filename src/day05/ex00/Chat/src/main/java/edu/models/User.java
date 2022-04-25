package edu.models;

import java.util.List;

public class User {

    private long id;
    private String login;
    private String password;
    private List<Chatroom> myCreatedRooms;
    private List<Chatroom> mySocializesRooms;
}
