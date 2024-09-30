package org.kiennguyenfpt.datingapp.repositories;

import org.kiennguyenfpt.datingapp.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    // Lấy tất cả tin nhắn giữa hai người dùng
    List<Message> findBySender_userIdAndReceiver_userId(Long senderId, Long receiverId);
}
