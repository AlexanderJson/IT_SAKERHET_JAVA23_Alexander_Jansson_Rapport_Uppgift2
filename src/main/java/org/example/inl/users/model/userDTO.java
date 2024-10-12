package org.example.inl.users.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class userDTO {

    private String username;

    public userDTO(String username) {
        this.username = username;
    }

}
