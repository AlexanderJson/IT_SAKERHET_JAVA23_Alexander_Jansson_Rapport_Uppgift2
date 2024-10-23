package org.example.inl.transactions.model;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.inl.users.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column
    private String name;

    @Column
    private String amount;

    @Column
    private LocalDate date;

    @Column(name = "is_recurring")
    private LocalDate isRecurring;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    @Column
    private Type type;

    @Column
    private String category;  // VARCHAR för kategori

    public enum Type {
        ONLINE_SHOPPING,
        IRL_SHOPPING,
        MONTHLY_EXPENSE
    }
}
