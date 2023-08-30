package com.example.meramot_back.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@Table(name = "comment")
public class Comment {
    @Id//Comment Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    // user id who made the comment
    @Column(name = "uid")
    private UUID uid;
    //Post ID
    @Column(name = "post_id")
    private Long post_id;
    // vote count
    @Column(name = "vote")
    private Integer vote;
    // timestamp
    @Column(name = "timestamp")
    private Timestamp timestamp;
    // content
    @Column(name = "content")
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Long getPost_id() {
        return post_id;
    }

    public void setPost_id(Long post_id) {
        this.post_id = post_id;
    }

    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", uid=" + uid +
                ", post_id=" + post_id +
                ", vote=" + vote +
                ", timestamp=" + timestamp +
                ", content='" + content + '\'' +
                '}';
    }
}
