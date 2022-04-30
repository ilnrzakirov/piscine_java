package edu.school21.sockets.services;

import edu.school21.sockets.models.User;

import java.util.Optional;

public interface UsersService {

    String hashPassword(String password);

    boolean saveUser(String username, String password);

    boolean checkUser(String username, String password);

    User getUserByLogin(String login);

    boolean singIn(String login, String password);
}
