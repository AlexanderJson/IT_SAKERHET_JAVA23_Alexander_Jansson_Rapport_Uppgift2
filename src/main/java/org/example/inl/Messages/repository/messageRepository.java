package org.example.inl.Messages.repository;

import org.example.inl.Messages.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface messageRepository extends JpaRepository<Messages, Long> {

    List<Messages> findByUserId(Long userId);

}
