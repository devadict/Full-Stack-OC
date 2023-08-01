package com.app.raghu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.raghu.entity.Post;
import com.app.raghu.entity.Topic;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByTopicIn(List<Topic> topics);
}
