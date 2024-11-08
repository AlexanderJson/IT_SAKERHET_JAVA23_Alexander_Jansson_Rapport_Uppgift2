package org.example.inl.Messages.service;


import org.example.inl.Security.Encryption.AESEncryption;
import org.example.inl.Messages.model.Messages;
import org.example.inl.Messages.model.MessagesDTO;
import org.example.inl.Messages.repository.messageRepository;
import org.example.inl.users.model.User;
import org.example.inl.users.repository.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class messageService {


    @Autowired
    private final messageRepository messageRepository;

    @Autowired
    private final userRepo UserRepo;

    @Autowired
    private final AESEncryption aesEncryption;


    public messageService(messageRepository messageRepository, userRepo UserRepo, AESEncryption aesEncryption) {
        this.messageRepository = messageRepository;
        this.UserRepo = UserRepo;
        this.aesEncryption = aesEncryption;
    }


    public Messages addMessage(MessagesDTO messagesDTO, String username) throws Exception {
        User user = UserRepo.findByEmail(username);

        Messages messages = new Messages();
        messages.setUserId(user.getId());
        String encryptedName = aesEncryption.encrypt(messagesDTO.getMessage());
        messages.setMessage(encryptedName);
        LocalDate created_at = LocalDate.now();
        messages.setCreatedAt(created_at);


        return messageRepository.save(messages);
    }




    public Long getUserIdFromUsername(String username) {
        User user = UserRepo.findByEmail(username);
        return user.getId();
    }


    // GET
    public List<Messages> getMessageByUserId(Long userId) throws Exception {

        List<Messages> messages = messageRepository.findByUserId(userId);
        for (Messages message : messages) {

            String decryptedName = aesEncryption.decrypt(message.getMessage());
            message.setMessage(decryptedName);

            LocalDate created_at = message.getCreatedAt();
            message.setCreatedAt(created_at);

        }

        return messageRepository.findByUserId(userId);
    }




}
