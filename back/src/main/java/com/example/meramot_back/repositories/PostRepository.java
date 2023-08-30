package com.example.meramot_back.repositories;

import com.example.meramot_back.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.post_id = ?1")
    Post findByPost_id(Long post_id);

    @Query("SELECT count(*) FROM Post p WHERE p.uid = ?1")
    int countByUid(UUID uid);
}
