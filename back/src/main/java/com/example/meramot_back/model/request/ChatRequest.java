package com.example.meramot_back.model.request;

import com.example.meramot_back.config.GptConfig;
import com.example.meramot_back.model.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ChatRequest {
    private String model;
    private List<Message> messages;
    private int n;
    private double temperature;
    private int max_tokens;

    public ChatRequest() {
        this.model = GptConfig.MODEL;
        this.messages = new ArrayList<>();
        this.n = 1;
        this.temperature = 0.2;
        this.max_tokens = 50;
    }
    public void add(String role, String content){
        this.messages.add(new Message(role, content));
    }
}
