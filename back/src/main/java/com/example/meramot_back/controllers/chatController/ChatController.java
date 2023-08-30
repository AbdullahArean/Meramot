package com.example.meramot_back.controllers.chatController;

import com.example.meramot_back.model.Message;
import com.example.meramot_back.model.response.ChatResponse;
import com.example.meramot_back.services.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private ChatService chatService;

    public ChatController(ChatService chatService){
        this.chatService = chatService;
    }

    @PostMapping("/send")
    public ResponseEntity<ChatResponse.Choice> sendMessage(@RequestBody List<Message> payload) throws JsonProcessingException {
        ArrayList<Message> prompt = new ArrayList<>(payload);
        ChatResponse response = chatService.getGptResponse(prompt);
        return new ResponseEntity<>(response.getChoices().get(0), HttpStatus.OK);
    }
}
