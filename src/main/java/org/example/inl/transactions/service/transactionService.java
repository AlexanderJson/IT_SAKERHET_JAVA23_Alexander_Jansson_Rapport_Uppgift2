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

import java.time.LocalDateTime;
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


    public transactionService(transactionRepo TransactionRepo, transactionRepo transactionRepo,userRepo UserRepo, AESEncryption aesEncryption) {
        this.TransactionRepo = transactionRepo;
        this.UserRepo = UserRepo;
        this.aesEncryption = aesEncryption;
    }


    public Transaction addTransaction(TransactionDTO transactionDTO, String username) throws Exception {
        User user = UserRepo.findByEmail(username);

        Transaction transaction = new Transaction();
        transaction.setUserId(user.getId());
        String encryptedAmount = aesEncryption.encrypt(String.valueOf(transactionDTO.getAmount()));
        transaction.setAmount(encryptedAmount);
        return TransactionRepo.save(transaction);
    }


    /*
    public void addTransaction(Long userId, Transaction transaction) {

        transaction.setUserId(userId);
        transaction.setCreatedAt(LocalDateTime.now());

        TransactionRepo.save(transaction);
    }*/


    // GET
    public List<Transaction> getTransactionsByUserId(Long userId){
        return TransactionRepo.findByUserId(userId);
    }


}
