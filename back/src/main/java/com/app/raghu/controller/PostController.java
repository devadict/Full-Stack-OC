package com.app.raghu.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.raghu.dto.request.CreateComment;
import com.app.raghu.dto.request.CreatePost;
import com.app.raghu.dto.response.CommentResponse;
import com.app.raghu.dto.response.PostListsResponse;
import com.app.raghu.dto.response.StringResponse;
import com.app.raghu.entity.Comment;
import com.app.raghu.entity.Post;
import com.app.raghu.service.ICommentService;
import com.app.raghu.service.IPostService;
import com.app.raghu.service.ITopicService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Post REST Endpoint", description = "Post manipulation")
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private IPostService postService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private ITopicService topicService;

    @ApiOperation(value = "Create an article")
    @PostMapping
    public ResponseEntity<StringResponse> createPost(@RequestBody CreatePost post) {

        Post createdPost = postService.createPost(post);
        return ResponseEntity.ok(new StringResponse("You successfully created the post" + createdPost.getTitle()));
    }
    
    @ApiOperation(value = "Retrieve postId and create comment for this article")
    @PostMapping("/{id}/comment")
    public ResponseEntity<StringResponse> createComment(@RequestBody CreateComment comment) {

        commentService.createComment(comment);

        return ResponseEntity.ok(new StringResponse("You successfully created the comment" + comment.getContent()));
    }

    @ApiOperation(value = "Get all published articles")
    @GetMapping
    public ResponseEntity<PostListsResponse> getPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Post>> getSinglePost(@PathVariable Integer id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @ApiOperation(value = "Get all comments for one article")
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Integer id) {
        return ResponseEntity.ok(commentService.findCommentsByPost(id));
    }
}
