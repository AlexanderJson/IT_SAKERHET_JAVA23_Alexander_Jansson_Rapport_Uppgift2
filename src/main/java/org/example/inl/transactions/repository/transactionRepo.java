package org.example.inl.transactions.repository;

import org.example.inl.transactions.model.Transaction;
import org.example.inl.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface transactionRepo extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserId(Long userId);

}
