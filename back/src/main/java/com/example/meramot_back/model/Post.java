package com.example.meramot_back.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "post_id", nullable = false, unique = true)
    private Long post_id;
    // user id who made the post
    @Column(name = "uid")
    private UUID uid;
    @Column(name = "vote")
    private Integer vote;
    @Column(name = "timestamp")
    private Timestamp timestamp;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;

    public Long getId() {
        return post_id;
    }

    public void setId(Long post_id) {
        this.post_id = post_id;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Post{" +
                "post_id=" + post_id +
                ", uid=" + uid +
                ", vote=" + vote +
                ", timestamp=" + timestamp +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
