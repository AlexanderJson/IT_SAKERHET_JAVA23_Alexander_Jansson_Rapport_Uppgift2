package org.example.inl.users.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.inl.transactions.model.Transaction;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String email;

    private String password;
}
