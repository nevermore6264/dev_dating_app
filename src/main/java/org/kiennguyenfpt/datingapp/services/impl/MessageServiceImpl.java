package org.kiennguyenfpt.datingapp.services.impl;

import org.kiennguyenfpt.datingapp.entities.Message;
import org.kiennguyenfpt.datingapp.repositories.MessageRepository;
import org.kiennguyenfpt.datingapp.services.MessageService;
import org.kiennguyenfpt.datingapp.services.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final NotificationService notificationService;

    public MessageServiceImpl(MessageRepository messageRepository, NotificationService notificationService) {
        this.messageRepository = messageRepository;
        this.notificationService = notificationService;
    }

    @Override
    public Message sendMessage(Long senderId, Long receiverId, String content) {
        /*
        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setContent(content);
        Message savedMessage = messageRepository.save(message);
        notificationService.sendNotification(receiverId, "You have a new message from user " + senderId);
        return savedMessage;

         */
        return null;
    }
}
