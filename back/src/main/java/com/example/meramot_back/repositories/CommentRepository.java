package com.example.meramot_back.repositories;

import com.example.meramot_back.model.Comment;
import lombok.experimental.PackagePrivate;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.post_id = ?1 ORDER BY c.vote DESC")
    List<Comment> findByPost_id(Long post_id);

    @Query("select count(*) from Comment c where c.post_id=:post_id")
    int commentPerPost(@Param("post_id")int post_id);
    @Query("SELECT c FROM Comment c WHERE c.uid = ?1")
    ArrayList<Comment> getCommentsByUid(UUID uid);
}
