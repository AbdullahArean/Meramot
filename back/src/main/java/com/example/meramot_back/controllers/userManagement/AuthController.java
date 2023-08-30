package com.example.meramot_back.controllers.userManagement;

import com.example.meramot_back.model.Comment;
import com.example.meramot_back.model.User;
import com.example.meramot_back.repositories.CommentRepository;
import com.example.meramot_back.repositories.PostRepository;
import com.example.meramot_back.repositories.PostVoteRepository;
import com.example.meramot_back.repositories.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ScopeNotActiveException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostVoteRepository postVoteRepository;


    public AuthController(UserRepository userRepository,
                          PostRepository postRepository,
                          CommentRepository commentRepository,
                          PostVoteRepository postVoteRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.postVoteRepository = postVoteRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Map<String, String >payload) throws IOException {
        String email = payload.get("email");
        String name = payload.get("name");
        String role = payload.get("role");
        String password = payload.get("password");
        String profile_pic = payload.get("photoURL");
        int count = userRepository.countByEmail(email);
        System.out.println(email + "\n" + name + "\n" + role + "\n" + password + "\n" + profile_pic + "\n" + count);
        try{
            User user;
            if (count == 0) {
                user = new User(UUID.randomUUID(), email, name, role, password, profile_pic);
                userRepository.save(user);
            }else{
                user = userRepository.getReferenceByEmail(email);
            }
            user.setPassword(null);
            return ResponseEntity.ok().body(user);
        }catch (ScopeNotActiveException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @PostMapping("/profile")
    public ResponseEntity<UserInfo> profile(@RequestBody Map<String, String >payload) throws IOException {
        UUID uid = UUID.fromString(payload.get("uid"));
        UserInfo userInfo = new UserInfo();
        if(uid!=null){
            userInfo.setComments(commentRepository.getCommentsByUid(uid));
            userInfo.setPostCnt(postRepository.countByUid(uid));
            userInfo.setVoteCnt(postVoteRepository.getPostVoteCntByUid(uid));
            return ResponseEntity.ok().body(userInfo);
        }
        return ResponseEntity.badRequest().body(null);
    }
    @Data
    private static class UserInfo{
        int postCnt = 0;
        int voteCnt = 0;
        ArrayList<Comment> comments = new ArrayList<>();

        public int getPostCnt() {
            return postCnt;
        }

        public void setPostCnt(int postCnt) {
            this.postCnt = postCnt;
        }

        public int getVoteCnt() {
            return voteCnt;
        }

        public void setVoteCnt(int voteCnt) {
            this.voteCnt = voteCnt;
        }

        public ArrayList<Comment> getComments() {
            return comments;
        }

        public void setComments(ArrayList<Comment> comments) {
            this.comments = comments;
        }
    }
}
