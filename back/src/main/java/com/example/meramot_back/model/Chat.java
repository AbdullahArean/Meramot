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
@Table(name = "chat")
public class Chat {
    @Id//chat id
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;
    // user id who initiated the chat
    @Column(name = "uid")
    private UUID uid;
    // time of creation of chat
    @Column(name = "created_at")
    private Timestamp created_at;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Timestamp created_at) {
        this.created_at = created_at;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", uid=" + uid +
                ", created_at=" + created_at +
                '}';
    }
}
