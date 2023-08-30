package com.example.meramot_back.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "post_vote")
public class PostVote {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Column(name = "post_id")
    private Long post_id;
    @Column(name = "uid")
    private UUID uid;
    @Column(name = "cnt")
    private Integer cnt;

    public PostVote() {
    }

    public PostVote(Long post_id, UUID uid, Integer cnt) {
        this.post_id = post_id;
        this.uid = uid;
        this.cnt = cnt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPost_id() {
        return post_id;
    }

    public void setPost_id(Long post_id) {
        this.post_id = post_id;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Integer getVote() {
        return cnt;
    }

    public void setVote(Integer vote) {
        this.cnt = vote;
    }
}
