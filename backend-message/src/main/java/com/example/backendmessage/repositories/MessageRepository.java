package com.example.backendmessage.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backendmessage.models.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<Message> findById(int id);

    @Query("SELECT m FROM Message m WHERE m.sender_user_id = :id")
    List<Message> findBySenderId(@Param("id") int senderId);

    @Query("SELECT m FROM Message m WHERE m.receiver_user_id = :id")
    List<Message> findByReceiverId(@Param("id") int receiverId);
}
