package com.app.raghu.service;

import java.util.List;

import com.app.raghu.dto.request.CreateComment;
import com.app.raghu.dto.response.CommentResponse;
import com.app.raghu.entity.Comment;

public interface ICommentService {
    
    public Comment createComment(CreateComment comment);

    public List<Comment> findCommentsByPost(Integer postId);
    public List<Comment> getAllComments();
}
