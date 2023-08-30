package com.example.meramot_back.repositories;

import com.example.meramot_back.model.PostVote;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostVoteRepository extends JpaRepository<PostVote, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Post p SET p.vote  = (SELECT SUM(pv.cnt) FROM PostVote pv WHERE pv.post_id = :post_id) WHERE p.post_id = :post_id")
    void updatePost(@Param("post_id") Integer post_id);

    @Modifying
    @Transactional
    @Query("UPDATE PostVote pv SET pv.cnt = :cnt WHERE pv.post_id = :post_id AND pv.uid = :uid")
    void updatePostVoteCntByPostIdAndUserId(@Param("post_id") Integer post_id,@Param("uid") UUID uid, @Param("cnt") Integer cnt);

    @Query("SELECT pv FROM PostVote pv WHERE pv.post_id = :post_id AND pv.uid = :uid")
    Object getPostVoteCntByPostIdAndUserId(@Param("post_id") Integer post_id, @Param("uid") UUID uid);

    @Query("SELECT pv FROM PostVote pv WHERE pv.uid = :uid AND pv.post_id = :post_id")
    PostVote getPostVoteCntByUidAndPostId(@Param("uid") UUID uid, @Param("post_id")Integer post_id);

    @Query("SELECT COUNT(*) FROM PostVote pv WHERE pv.post_id = :post_id AND pv.uid = :uid")
    int getPostVoteCntByPostIdAndUserIdAndPost_id(@Param("post_id") Integer post_id, @Param("uid") UUID uid);

    @Query("SELECT COUNT(*) FROM PostVote pv WHERE pv.uid = :uid")
    int getPostVoteCntByUid(UUID uid);
}
