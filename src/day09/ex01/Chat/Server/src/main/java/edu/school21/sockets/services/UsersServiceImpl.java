package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersServiceImpl implements  UsersService{

    private PasswordEncoder passwordEncoder;
    private UsersRepository usersRepository;

    @Autowired
    private void setPasswordEncoder(PasswordEncoder passwordEncoder){
        this.passwordEncoder =  passwordEncoder;
    }

    @Autowired
    private void setUsersRepository(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    @Override
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean saveUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        Optional <User> findUser = usersRepository.findByUsername(username);

        if (findUser.isPresent()){
            return true;
        }

        user.setPassword(passwordEncoder.encode(password));
        usersRepository.save(user);
        return false;
    }

    @Override
    public boolean checkUser(String username, String password) {
        Optional <User> findUser = usersRepository.findByUsername(username);

        if (findUser.isPresent()){
            return true;
        }

        return false;
    }

    @Override
    public User getUserByLogin(String login) {
        Optional <User> findUser = usersRepository.findByUsername(login);
        return findUser.get();
    }

    @Override
    public boolean singIn(String login, String password) {
        User user = getUserByLogin(login);
        if (passwordEncoder.matches(password, user.getPassword())){
            return true;
        } else {
            return false;
        }
    }
}
