package com.app.raghu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.raghu.entity.Topic;

public interface TopicRepository extends JpaRepository<Topic, Integer>{
    
}
