package edu.models;

import java.util.List;
import java.util.Objects;

public class Chatroom {

    private long id;
    private String name;
    private User owner;
    private List<Message> massageList;

    public Chatroom(long id, String name) {
        this.id = id;
        this.name = name;
        this.owner = null;
        this.massageList = null;
    }

    @Override
    public String toString() {
        return "Chatroom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", massageList=" + massageList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chatroom chatroom = (Chatroom) o;
        return id == chatroom.id && Objects.equals(name, chatroom.name) && Objects.equals(owner, chatroom.owner) && Objects.equals(massageList, chatroom.massageList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner, massageList);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }
}
