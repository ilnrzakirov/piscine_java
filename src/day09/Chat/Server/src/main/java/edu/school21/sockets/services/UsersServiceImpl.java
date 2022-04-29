package edu.school21.sockets.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UsersServiceImpl implements  UsersService{

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
