package edu.school21.sockets.services;

public interface UsersService {

    String hashPassword(String password);

    boolean saveUser(String username, String password);
}
