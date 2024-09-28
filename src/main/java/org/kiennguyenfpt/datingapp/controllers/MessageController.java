package org.kiennguyenfpt.datingapp.controllers;

import org.kiennguyenfpt.datingapp.dtos.requests.MessageRequest;
import org.kiennguyenfpt.datingapp.entities.Message;
import org.kiennguyenfpt.datingapp.responses.CommonResponse;
import org.kiennguyenfpt.datingapp.services.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/messages")
public class MessageController {
    /*
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    public ResponseEntity<CommonResponse<Message>> sendMessage(@RequestBody MessageRequest messageRequest) {
        CommonResponse<Message> response = new CommonResponse<>();
        try {
            Message message = messageService.sendMessage(messageRequest.getSenderId(), messageRequest.getReceiverId(), messageRequest.getContent());
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Message sent successfully.");
            response.setData(message);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error sending message: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

     */
}
