package com.example.meramot_back.services;

import com.example.meramot_back.config.GptConfig;
import com.example.meramot_back.model.Message;
import com.example.meramot_back.model.request.ChatRequest;
import com.example.meramot_back.model.response.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class ChatService {
    private final RestTemplate restTemplate;
    @Autowired
    private Environment env;

    public ChatService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ChatResponse getGptResponse( ArrayList<Message> prompt){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","Bearer "+env.getProperty("OPENAI_API_KEY"));

        ChatRequest request = new ChatRequest();
        prompt.forEach(msg->{
            request.add(msg.role, msg.content);
        });

        HttpEntity<ChatRequest> gptRequest = new HttpEntity<>(request, headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(GptConfig.URL, gptRequest, ChatResponse.class);
    }
}
