package org.example.inl.transactions.service;


import org.example.inl.Security.Encryption.AESEncryption;
import org.example.inl.transactions.model.Transaction;
import org.example.inl.transactions.model.TransactionDTO;
import org.example.inl.transactions.repository.transactionRepo;
import org.example.inl.users.model.User;
import org.example.inl.users.model.userDTO;
import org.example.inl.users.repository.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class transactionService {


    @Autowired
    private final transactionRepo TransactionRepo;

    @Autowired
    private final userRepo UserRepo;

    @Autowired
    private final AESEncryption aesEncryption;


    public transactionService( transactionRepo transactionRepo,userRepo UserRepo, AESEncryption aesEncryption) {
        this.TransactionRepo = transactionRepo;
        this.UserRepo = UserRepo;
        this.aesEncryption = aesEncryption;
    }


    public Transaction addTransaction(TransactionDTO transactionDTO, String username) throws Exception {
        User user = UserRepo.findByEmail(username);

        Transaction transaction = new Transaction();
        transaction.setUserId(user.getId());
        String encryptedName = aesEncryption.encrypt(transactionDTO.getName());
        transaction.setName(encryptedName);
        String encryptedAmount = aesEncryption.encrypt(transactionDTO.getAmount());
        transaction.setAmount(encryptedAmount);
        LocalDate created_at = LocalDate.now();
        transaction.setCreatedAt(created_at);


        return TransactionRepo.save(transaction);
    }


    /*
    public void addTransaction(Long userId, Transaction transaction) {

        transaction.setUserId(userId);
        transaction.setCreatedAt(LocalDateTime.now());

        TransactionRepo.save(transaction);
    }*/


    public Long getUserIdFromUsername(String username) {
        User user = UserRepo.findByEmail(username);
        return user.getId();
    }


    // GET
    public List<Transaction> getTransactionsByUserId(Long userId) throws Exception {

        List<Transaction> transactions = TransactionRepo.findByUserId(userId);
        for (Transaction transaction : transactions) {

            String decryptedName = aesEncryption.decrypt(transaction.getName());
            transaction.setName(decryptedName);

            String decryptedAmount = aesEncryption.decrypt(transaction.getAmount());
            transaction.setAmount(decryptedAmount);

            LocalDate created_at = transaction.getCreatedAt();
            transaction.setCreatedAt(created_at);

        }

        return TransactionRepo.findByUserId(userId);
    }




}
