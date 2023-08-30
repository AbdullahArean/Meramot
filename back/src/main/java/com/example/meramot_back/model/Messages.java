package com.example.meramot_back.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@Table(name = "messages")
public class Messages {
    @Id//message id
    @Column(name = "mid", nullable = false, unique = true)
    private Long mid;
    //chat id in which message is the part of chat
    @Column(name = "chat_id", nullable = false)
    private UUID chat_id;
    @Column(name = "message")
    private String message;
    @Column(name = "sender_id")
    private UUID sender_id;
    @Column(name = "timestamp")
    private Timestamp timestamp;
    private String role;

    public void setRole(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
    public Long getMid() {
        return mid;
    }
    public void setMid(Long mid) {
        this.mid = mid;
    }

    public UUID getUid() {
        return chat_id;
    }

    public void setUid(UUID chat_id) {
        this.chat_id = chat_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UUID getSender_id() {
        return sender_id;
    }

    public void setSender_id(UUID sender_id) {
        this.sender_id = sender_id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Messages{" +
                "role='" + role + '\'' +
                "mid=" + mid +
                ", chat_id=" + chat_id +
                ", message='" + message + '\'' +
                ", sender_id=" + sender_id +
                ", timestamp=" + timestamp +
                '}';
    }
}
