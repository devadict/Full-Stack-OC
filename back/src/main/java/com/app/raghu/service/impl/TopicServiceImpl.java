package com.app.raghu.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.raghu.dto.response.TopicListsResponse;
import com.app.raghu.entity.Topic;
import com.app.raghu.entity.User;
import com.app.raghu.repository.TopicRepository;
import com.app.raghu.repository.UserRepository;
import com.app.raghu.service.ITopicService;

import lombok.Data;

@Data
@Service
public class TopicServiceImpl implements ITopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Topic> getAllTopics() {
        // List<Topic> topic = topicRepository.findAll();
        return topicRepository.findAll();
    }

    public Optional<Topic> getTopicById(Integer id) {
        return topicRepository.findById(id);
    }

    // @Transactional
    // public void subscribe(Integer userId, Integer topicId) {
    //     User user = userRepository.findById(userId)
    //             .orElseThrow(() -> new RuntimeException("User not found"));

    //     Topic topic = topicRepository.findById(topicId)
    //             .orElseThrow(() -> new RuntimeException("Topic not found"));

    //     List<Topic> subscribedTopics = user.getSubscribed();

    //     // Check if the user is already subscribed to the topic
    //     if (subscribedTopics.contains(topic)) {
    //         throw new RuntimeException("User is already subscribed to this topic");
    //     }

    //     subscribedTopics.add(topic);
    //     user.setSubscribed(subscribedTopics);

    //     userRepository.save(user);
    // }

    @Transactional
    public void subscribe(Integer userId, Integer topicId) {
        User user = userRepository.findById(userId).orElse(null);
        Topic topic = topicRepository.findById(topicId).orElse(null);

        if (user != null && topic != null) {
            List<User> subscribers = topic.getSubscribers();
            if (!subscribers.contains(user)) {
                subscribers.add(user);
                topicRepository.save(topic);
            }
        }
    }
    
    @Transactional
    public void unsubscribeFromTopic(Integer userId, Integer topicId) {
        User user = userRepository.findById(userId).orElse(null);
        Topic topic = topicRepository.findById(topicId).orElse(null);

        if (user != null && topic != null) {
            List<User> subscribers = topic.getSubscribers();
            if (subscribers.contains(user)) {
                subscribers.remove(user);
                topicRepository.save(topic);
            }
        }
    }
    
    // @Transactional
    // public void unsubscribeFromTopic(Integer userId, Integer topicId) {
    //     User user = userRepository.findById(userId)
    //             .orElseThrow(() -> new RuntimeException("User not found"));

    //     Topic topic = topicRepository.findById(topicId)
    //             .orElseThrow(() -> new RuntimeException("Topic not found"));

    //     List<Topic> subscribedTopics = user.getSubscribed();

    //     // Check if the user is subscribed to the topic
    //     if (!subscribedTopics.contains(topic)) {
    //         throw new RuntimeException("User is not subscribed to this topic");
    //     }

    //     subscribedTopics.remove(topic);
    //     user.setSubscribed(subscribedTopics);

    //     userRepository.save(user);
    // }
}
