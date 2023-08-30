package com.example.meramot_back.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "categories")
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "post_id", nullable = false, unique = false)
    private Long post_id;
    // Tag of the post
    @Column(name = "category")
    private String category;


    public Categories() {
    }
    public Categories(Long post_id, String category) {
        this.post_id = post_id;
        this.category = category;
    }

    public Long getUid() {
        return post_id;
    }

    public void setUid(Long post_id) {
        this.post_id = post_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Categories{" + "post_id=" + post_id + ", category=" + category + '}';
    }
}
