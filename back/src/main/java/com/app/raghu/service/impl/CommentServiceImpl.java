package com.app.raghu.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.raghu.dto.request.CreateComment;
import com.app.raghu.dto.response.CommentResponse;
import com.app.raghu.entity.Comment;
import com.app.raghu.entity.Post;
import com.app.raghu.entity.User;
import com.app.raghu.repository.CommentRepository;
import com.app.raghu.repository.PostRepository;
import com.app.raghu.repository.UserRepository;
import com.app.raghu.service.ICommentService;

import lombok.Data;

@Data
@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Transactional
    public Comment createComment(CreateComment comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> userExists = userRepository.findByUsername(username);

        User user = userExists.get();
        
        Optional<Post> postExists = postRepository.findById(comment.getPostId());

        Post post = postExists.get();

        Comment newComment = new Comment();
        newComment.setUser(user);
        newComment.setPost(post);
        newComment.setContent(comment.getContent());

        commentRepository.save(newComment);

        return newComment;
    }

    public List<Comment> findCommentsByPost(Integer postId) {
        return commentRepository.findAllByPostId(postId);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }


}
