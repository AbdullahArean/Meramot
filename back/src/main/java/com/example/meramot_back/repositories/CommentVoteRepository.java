package com.example.meramot_back.repositories;

import com.example.meramot_back.model.CommentVote;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface CommentVoteRepository extends JpaRepository<CommentVote, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Comment c SET c.vote  = (SELECT SUM(cv.cnt) FROM CommentVote cv WHERE cv.comment_id = :comment_id) WHERE c.id = :comment_id")
    void updateCommentVote(@Param("comment_id") Long comment_id);

    @Query("SELECT cv FROM CommentVote cv WHERE cv.uid = :uid AND cv.post_id = :post_id")
    ArrayList<CommentVote> getCommentVoteCntByUidAndPostId(@Param("uid") UUID uid, @Param("post_id")Long post_id);

    @Query("SELECT COUNT(*) FROM CommentVote cv WHERE cv.comment_id = :comment_id")
    int getCommentVoteCntByCommentIdAndUserIdAndPost_id(@Param("comment_id") Long comment_id);

    @Modifying
    @Transactional
    @Query("UPDATE CommentVote cv SET cv.cnt = :cnt WHERE cv.comment_id = :comment_id")
    void updateCommentVoteCntByCommentIdAndUserIdAndPost_id(@Param("comment_id") Long comment_id,@Param("cnt") Integer cnt);
}
