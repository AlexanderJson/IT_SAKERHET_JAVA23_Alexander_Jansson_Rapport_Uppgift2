package org.example.inl.users.repository;

import org.example.inl.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepo extends JpaRepository<User, Long> {


    User findByEmail(String email);

    User findById(long id);

}
