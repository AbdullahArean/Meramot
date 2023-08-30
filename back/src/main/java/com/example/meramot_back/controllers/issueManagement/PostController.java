package com.example.meramot_back.controllers.issueManagement;

import com.example.meramot_back.model.*;
import com.example.meramot_back.repositories.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.http.HttpClient;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostRepository postRepository;
    private final CategoriesRepository categoriesRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostVoteRepository postVoteRepository;
    private final CommentVoteRepository commentVoteRepository;

    @Autowired
    public PostController(PostRepository postRepository,
                          CategoriesRepository categoriesRepository,
                          UserRepository userRepository,
                          CommentRepository commentRepository,
                          PostVoteRepository postVoteRepository,
                          CommentVoteRepository commentVoteRepository) {
        this.postRepository = postRepository;
        this.categoriesRepository = categoriesRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.postVoteRepository = postVoteRepository;
        this.commentVoteRepository = commentVoteRepository;
    }

    //Returns all the posts
    @GetMapping("/all")
    public ResponseEntity<Iterable<AllPost>> getAllPosts() {
        ArrayList<Post> posts = (ArrayList<Post>) postRepository.findAll();
        ArrayList<Categories> tags = (ArrayList<Categories>) categoriesRepository.findAll();
        ArrayList<AllPost> allPosts = new ArrayList<>();
        posts.sort((o1, o2) -> o2.getVote().compareTo(o1.getVote()));
        for (Post post : posts) {
            AllPost allPost = new AllPost();
            allPost.setPost(post);
            User user = userRepository.getReferenceByUser_id(post.getUid());
            allPost.setAuthorName(user.getName());
            allPost.setAuthorPic(user.getProfile_pic());
            int post_id = post.getPost_id().intValue();
            int answerCnt = commentRepository.commentPerPost(post_id);
            allPost.setAnswerCnt(answerCnt);
            for (Categories tag : tags) {
                if (tag.getPost_id().equals(post.getPost_id())) {
                    allPost.setTags(tag.getCategory());
                }
            }
            allPosts.add(allPost);
        }
        return ResponseEntity.ok(allPosts);
    }

    //Creates a new Post
    @PostMapping("/create")
    public ResponseEntity<String> makePost(@RequestBody Map<String, Object> payload) throws IOException {
        Post post = new Post();
        //user id who made the post
        post.setUid((UUID.fromString((String) payload.get("id"))));
        //Initial Vote count set to 0
        post.setVote(0);
        //Time of post
        post.setTimestamp(new Timestamp(System.currentTimeMillis()));
        //Title of post
        post.setTitle((String) payload.get("title"));
        //Problem Description of post
        post.setContent((String) payload.get("content"));

        try {
            postRepository.save(post);
            try {
                ArrayList<String> categories = (ArrayList<String>) payload.get("tags");
                for (String category : categories) {
                    Categories categories1 = new Categories(post.getPost_id(), category);
                    System.out.println(categories1.toString());
                    categoriesRepository.save(categories1);
                }
            } catch (Exception e) {
                return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Problem Posted Successfully", HttpStatus.OK);
    }

    //Returns a post by id
    @PostMapping("/get-post")
    public ResponseEntity<AllPost> getPostById(@RequestBody Map<String, Object> payload) {
        int post_id = Integer.parseInt((String) payload.get("post_id"));
        UUID uid = null;
        try {
            uid = UUID.fromString((String) payload.get("uid"));
        } catch (Exception e) {
            System.out.println("No uid");
        }

        AllPost allPost = new AllPost();
        try {
            Post post = postRepository.findById((long) post_id).orElse(null);
            allPost.setPost(post);
            try {
                ArrayList<Categories> tags = (ArrayList<Categories>) categoriesRepository.findByPost_id((long) post_id);
                for (Categories tag : tags) {
                    allPost.setTags(tag.getCategory());
                }
                assert post != null;
                User user = userRepository.getReferenceByUser_id(post.getUid());
                allPost.setAuthorName(user.getName());
                allPost.setAuthorPic(user.getProfile_pic());

                ArrayList<Comment> comments = (ArrayList<Comment>) commentRepository.findByPost_id((long) post_id);
                Map<Long, CommentVote> map = new HashMap<>();
                PostVote postVote = null;
                if (uid != null) {
                    ArrayList<CommentVote> commentVotes = (ArrayList<CommentVote>) commentVoteRepository.getCommentVoteCntByUidAndPostId(uid, (long) post_id);
                    postVote = postVoteRepository.getPostVoteCntByUidAndPostId(uid, post_id);
                    allPost.setPostVote(postVote);
                    commentVotes.forEach(commentVote -> {
                        map.put(commentVote.getComment_id(), commentVote);
                    });
                }
                for (Comment comment : comments) {
                    User user1 = userRepository.getReferenceByUser_id(comment.getUid());
                    Solution solution = new Solution();
                    solution.setComment(comment);
                    solution.setAuthorName(user1.getName());
                    solution.setAuthorPic(user1.getProfile_pic());
                    solution.setCommentVote(map.get(comment.getId()));
                    allPost.addSolution(solution);
                }
            } catch (Exception e) {
                allPost.setTags(null);
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(allPost);
    }

    @PostMapping("/do-comment")
    public ResponseEntity<String> doComment(@RequestBody Map<String, Object> payload) throws IOException {
        Comment comment = new Comment();
        payload.forEach((key, value) -> {
            System.out.println(key + " " + value);
        });
        comment.setUid(UUID.fromString((String) payload.get("uid")));
        comment.setPost_id(Long.parseLong((String) payload.get("post_id")));
        comment.setTimestamp(new Timestamp(System.currentTimeMillis()));
        comment.setVote(0);
        comment.setContent((String) payload.get("content"));
        commentRepository.save(comment);
        return new ResponseEntity<>("Commented Successfully", HttpStatus.OK);
    }

    @PostMapping("/get-comments")
    public ResponseEntity<ArrayList<Solution>> getComments(@RequestBody Map<String, Object> payload) {
        Long id = Long.parseLong((String) payload.get("post_id"));
        UUID uid = null;
        try {
            uid = UUID.fromString((String) payload.get("uid"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Comment> comments = (ArrayList<Comment>) commentRepository.findByPost_id(id);
        ArrayList<Solution> solutions = new ArrayList<>();
        Map<Long, CommentVote> map = new HashMap<>();
        if (uid != null) {
            ArrayList<CommentVote> commentVotes = (ArrayList<CommentVote>) commentVoteRepository.getCommentVoteCntByUidAndPostId(uid, id);
            commentVotes.forEach(commentVote -> {
                map.put(commentVote.getComment_id(), commentVote);
            });
        }
        for (Comment comment : comments) {
            User user = userRepository.getReferenceByUser_id(comment.getUid());
            Solution solution = new Solution();
            solution.setComment(comment);
            solution.setAuthorName(user.getName());
            solution.setAuthorPic(user.getProfile_pic());
            solution.setCommentVote(map.get(comment.getId()));
            solutions.add(solution);
        }
        return ResponseEntity.ok(solutions);
    }

    @PostMapping("/vote-post")
    public ResponseEntity<Object> postVote(@RequestBody Map<String, Object> payload) {
        System.out.println("Request to post vote");

        UUID uid = UUID.fromString((String) payload.get("uid"));
        Long post_id = Long.parseLong((String) payload.get("post_id"));
        Integer post_id_int = Integer.parseInt((String) payload.get("post_id"));
        int vote = (Integer) payload.get("vote");
        try {
            PostVote postVote = new PostVote();
            postVote.setUid(uid);
            postVote.setPost_id(post_id);
            postVote.setCnt(vote);
            int cnt = postVoteRepository.getPostVoteCntByPostIdAndUserIdAndPost_id(((Number)post_id).intValue(), uid);
            if(cnt==0){
                postVoteRepository.save(postVote);
            }else{
                postVoteRepository.updatePostVoteCntByPostIdAndUserId(Math.toIntExact(post_id), uid, vote);
            }
            postVoteRepository.updatePost(post_id_int);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok("success");
    }

    @PostMapping("/vote-comment")
    public ResponseEntity<Object> commentVote(@RequestBody Map<String, Object> payload) {
        System.out.println("Request to Comment vote");
        try {
            Long post_id = Long.parseLong((String) payload.get("post_id"));
            Long comment_id = ((Number) payload.get("comment_id")).longValue();
            UUID uid = UUID.fromString((String) payload.get("uid"));
            Integer cnt = (Integer) payload.get("vote");
            System.out.println(post_id + " " + comment_id + " " + uid + " " + cnt);
            CommentVote commentVote = new CommentVote();
            commentVote.setPost_id(post_id);
            commentVote.setComment_id(comment_id);
            commentVote.setUid(uid);
            commentVote.setCnt(cnt);
            System.out.println( commentVoteRepository.getCommentVoteCntByCommentIdAndUserIdAndPost_id(comment_id));
            int cnt1 = commentVoteRepository.getCommentVoteCntByCommentIdAndUserIdAndPost_id(comment_id);
            if(cnt1 == 0) {
                commentVoteRepository.save(commentVote);
            } else {
                commentVoteRepository.updateCommentVoteCntByCommentIdAndUserIdAndPost_id(comment_id, cnt);
            }
            commentVoteRepository.updateCommentVote(comment_id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Voted Successfully", HttpStatus.OK);
    }

    @PostMapping("/vote-info")
    public ResponseEntity<Object> getPostVoteInfo(@RequestBody Map<String, Object> payload) {
        System.out.println("Request to get post vote info");
        try {
            Integer post_id = Integer.parseInt((String) payload.get("post_id"));
            UUID uid = UUID.fromString((String) payload.get("uid"));
            return ResponseEntity.ok(postVoteRepository.getPostVoteCntByPostIdAndUserId(post_id, uid));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Data
    private static class AllPost {
        Post post = new Post();
        ArrayList<String> tags = new ArrayList<>();
        ArrayList<Solution> solutions = new ArrayList<>();
        PostVote postVote = new PostVote();
        String authorName = "";
        String authorPic = "";
        int answerCnt = 0;

        public void setPostVote(PostVote postVote) {
            this.postVote = postVote;
        }

        public PostVote getPostVote() {
            return postVote;
        }

        public void addSolution(Solution solution) {
            this.solutions.add(solution);
        }

        public void setAnswerCnt(int answerCnt) {
            this.answerCnt = answerCnt;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public void setAuthorPic(String authorPic) {
            this.authorPic = authorPic;
        }

        public void setPost(Post post) {
            this.post = post;
        }

        public void setTags(String tag) {
            this.tags.add(tag);
        }
    }

    @Data
    private static class Solution {
        Comment comment = new Comment();
        CommentVote commentVote = new CommentVote();
        String authorName = "";
        String authorPic = "";

        public void setComment(Comment comment) {
            this.comment = comment;
        }

        public void setCommentVote(CommentVote commentVote) {
            this.commentVote = commentVote;
        }
    }
}
