package org.example.inl.transactions.model;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TransactionDTO {

    private String name;
    private String amount;
    private LocalDate createdAt;
}
