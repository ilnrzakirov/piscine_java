package edu.school21.sockets.services;

import edu.school21.sockets.repositories.MessageRepository;
import edu.school21.sockets.repositories.MessageRepositoryImpl;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService{

    private MessageRepository messageRepository;

    @Autowired
    private void setUsersRepository(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    @Override
    public void saveMessage(String message) {
        messageRepository.save(message);
    }
}
