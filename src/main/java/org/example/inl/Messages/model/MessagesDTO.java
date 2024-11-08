package org.example.inl.Messages.model;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MessagesDTO {

    private String message;
    private LocalDate createdAt;
}
