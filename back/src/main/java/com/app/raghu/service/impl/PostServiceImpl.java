package com.app.raghu.service.impl;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.raghu.dto.request.CreatePost;
import com.app.raghu.dto.response.PostListsResponse;
import com.app.raghu.entity.Post;
import com.app.raghu.entity.Topic;
import com.app.raghu.entity.User;
import com.app.raghu.repository.CommentRepository;
import com.app.raghu.repository.PostRepository;
import com.app.raghu.repository.TopicRepository;
import com.app.raghu.repository.UserRepository;
import com.app.raghu.service.IPostService;

import lombok.Data;

@Data
@Service
public class PostServiceImpl implements IPostService {
    
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    public PostListsResponse getAllPosts() {
        List<Post> post = postRepository.findAll();
        return new PostListsResponse(post);
    }

    // public List<Post> getAllPosts() {
    //     // List<Post> post = postRepository.findAll();
    //     return postRepository.findAll();
    // }

    public Optional<Post> getPostById(Integer id) {
        return postRepository.findById(id);
    }

    @Transactional
    public Post createPost(CreatePost post) {
        
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        Optional<User> userExists = userRepository.findByUsername(username);

        User user = userExists.get();


        Optional<Topic> topic = topicRepository.findById(post.getTopicId());
        Post newPost = new Post();
        newPost.setAuthor(user);
        newPost.setTitle(post.getTitle());
        newPost.setContent(post.getContent());
        newPost.setTopic(topic.get());

        postRepository.save(newPost);

        return newPost;
    }
    
    
    public List<Post> getPersonnalizedFeed(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        List<Topic> subscribedTopics = user.getSubscribed();

        return postRepository.findByTopicIn(subscribedTopics);

    }
}
