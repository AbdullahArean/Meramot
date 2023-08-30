package com.example.meramot_back.model.response;

import com.example.meramot_back.model.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {
    public String id;
    public String object;
    public int created;
    public List<Choice> choices;
    public Usage usage;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Choice{
        public int index;
        public Message message;
        public String finish_reason;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Usage {
        public int prompt_tokens;
        public int completion_tokens;
        public int total_tokens;
    }
}
