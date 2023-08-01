package com.app.raghu.service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import com.app.raghu.dto.request.CreatePost;
import com.app.raghu.dto.response.PostListsResponse;
import com.app.raghu.entity.Post;

public interface IPostService {
    public PostListsResponse getAllPosts();
    
    // public List<Post> getAllPosts();

    public Optional<Post> getPostById(Integer id);

    public Post createPost(CreatePost post);
}
