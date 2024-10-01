//package org.kiennguyenfpt.datingapp.services.impl;
//
//import org.kiennguyenfpt.datingapp.entities.Message;
//import org.kiennguyenfpt.datingapp.entities.User;
//import org.kiennguyenfpt.datingapp.repositories.UserRepository;
//import org.kiennguyenfpt.datingapp.repositories.MessageRepository;
//import org.kiennguyenfpt.datingapp.services.MessageService;
//import org.kiennguyenfpt.datingapp.services.NotificationService;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class MessageServiceImpl implements MessageService {
//    private final MessageRepository messageRepository;
//    private final NotificationService notificationService;
//    private final UserRepository userRepository;
//
//    public MessageServiceImpl(MessageRepository messageRepository, NotificationService notificationService, UserRepository userRepository) {
//        this.messageRepository = messageRepository;
//        this.notificationService = notificationService;
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public Message sendMessage(Long senderId, Long receiverId, String content) {
//        User sender = userRepository.findById(senderId)
//                .orElseThrow(() -> new RuntimeException("Sender not found")); // Tìm người gửi
//        User receiver = userRepository.findById(receiverId)
//                .orElseThrow(() -> new RuntimeException("Receiver not found")); // Tìm người nhận
//
//        Message message = new Message();
//        message.setSender(sender);
//        message.setReceiver(receiver);
//        message.setContent(content);
//        // Thêm logic lưu message vào repository và gửi thông báo
//        Message savedMessage = messageRepository.save(message);
//        notificationService.sendNotification(receiverId, "You have a new message from user " + senderId);
//        return savedMessage;
//    }
//
//    @Override
//    public List<Message> getMessages(Long userId1, Long userId2) {
//        // Lấy danh sách tin nhắn giữa hai người dùng
//        return messageRepository.findBySender_userIdAndReceiver_userId(userId1, userId2);
//    }
//}
