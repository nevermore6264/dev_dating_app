package org.kiennguyenfpt.datingapp.services.impl;

import org.kiennguyenfpt.datingapp.entities.Match;
import org.kiennguyenfpt.datingapp.entities.Message;
import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.enums.MessageStatus;
import org.kiennguyenfpt.datingapp.repositories.MatchRepository;
import org.kiennguyenfpt.datingapp.repositories.UserRepository;
import org.kiennguyenfpt.datingapp.repositories.MessageRepository;
import org.kiennguyenfpt.datingapp.services.MessageService;
import org.kiennguyenfpt.datingapp.services.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final MatchRepository matchRepository; // Repository để tìm match

    public MessageServiceImpl(MessageRepository messageRepository, UserRepository userRepository, MatchRepository matchRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.matchRepository = matchRepository;
    }

    @Override
    public Message sendMessage(Long matchId, Long senderId, Long receiverId, String content) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found")); // Kiểm tra match hợp lệ

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found")); // Kiểm tra người gửi hợp lệ

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found")); // Kiểm tra người nhận hợp lệ

        Message message = new Message();
        message.setMatch(match);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);

        // Thiết lập trạng thái mặc định cho tin nhắn
        message.setStatus(MessageStatus.SENT); // Hoặc giá trị phù hợp với enum của bạn

        // Lưu message vào repository
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getMessages(Long matchId) {
        // Lấy danh sách tin nhắn theo matchId
        return messageRepository.findByMatch_MatchId(matchId);
    }
}
