package org.kiennguyenfpt.datingapp.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    private Long matchId;       // ID của match
    private Long senderId;      // ID của người gửi
    private Long receiverId;    // ID của người nhận
    private String content;     // Nội dung tin nhắn
}
