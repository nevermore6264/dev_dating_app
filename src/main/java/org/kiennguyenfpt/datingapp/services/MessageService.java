package org.kiennguyenfpt.datingapp.services;

import org.kiennguyenfpt.datingapp.entities.Message;

public interface MessageService {
    Message sendMessage(Long senderId, Long receiverId, String content);
}
