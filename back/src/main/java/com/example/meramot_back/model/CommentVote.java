package com.example.meramot_back.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "comment_vote")
public class CommentVote {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Column(name = "comment_id")
    private Long comment_id;
    @Column(name = "post_id")
    private Long post_id;
    @Column(name = "uid")
    private UUID uid;
    @Column(name = "cnt")
    private Integer cnt;

    public CommentVote() {
    }
    public CommentVote(Long comment_id, UUID uid, Integer cnt) {
        this.comment_id = comment_id;
        this.uid = uid;
        this.cnt = cnt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getComment_id() {
        return comment_id;
    }

    public void setComment_id(Long comment_id) {
        this.comment_id = comment_id;
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

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }
}
