package com.app.raghu.service;

import java.util.List;
import java.util.Optional;

import com.app.raghu.dto.response.TopicListsResponse;
import com.app.raghu.entity.Topic;

public interface ITopicService {
    public List<Topic> getAllTopics();

    public Optional<Topic> getTopicById(Integer id);

    public void subscribe(Integer userId, Integer topicId);
    
    public void unsubscribeFromTopic(Integer userId, Integer topicId);
}
