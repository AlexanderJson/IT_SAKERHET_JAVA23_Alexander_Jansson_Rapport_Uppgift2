package org.example.inl.transactions.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.inl.Security.JWT.JwTUtil;
import org.example.inl.transactions.model.Transaction;
import org.example.inl.transactions.model.TransactionDTO;
import org.example.inl.transactions.service.transactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class regTransaction {

    @Autowired
    private transactionService Transactionservice;
    @Autowired
    private JwTUtil jwTUtil;


    @GetMapping("/user/{userId}")
    public List<Transaction> getTransactionByUserId(@PathVariable Long userId) {
        return Transactionservice.getTransactionsByUserId(userId);
    }

    @PostMapping("/add")
    public ResponseEntity<Transaction> addTransaction(@RequestBody TransactionDTO transactionDTO,
                                                      @RequestHeader("Authorization") String token) throws Exception {
        String username = jwTUtil.extractedUsername(token.substring(7));
        Transaction transaction = Transactionservice.addTransaction(transactionDTO, username);
        return ResponseEntity.ok(transaction);
    }

    /*
    @PostMapping("/add")
    public ResponseEntity<?> addTransaction(HttpServletRequest request, @RequestBody Transaction transaction) {

        return ResponseEntity.ok("Transaction added");
    }*/
}

