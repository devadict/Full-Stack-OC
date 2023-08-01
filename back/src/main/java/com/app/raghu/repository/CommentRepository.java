package com.app.raghu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.raghu.dto.response.CommentResponse;
import com.app.raghu.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByPostId(Integer postId);
}
