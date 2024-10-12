package org.example.inl.users.repository;

import org.example.inl.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepo extends JpaRepository<User, Long> {


    User findByEmail(String email);

}
