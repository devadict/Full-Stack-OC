package com.app.raghu.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.raghu.dto.response.StringResponse;
import com.app.raghu.dto.response.TopicListsResponse;
import com.app.raghu.entity.Topic;
import com.app.raghu.entity.User;
import com.app.raghu.repository.UserRepository;
import com.app.raghu.service.ITopicService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Topic REST Endpoint", description = "Topic manipulation")
@RequestMapping("/api/topic")
public class TopicController {

    @Autowired
    private ITopicService topicService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Topic>> getAllTopics() {
        return ResponseEntity.ok(topicService.getAllTopics());
    }

    @ApiOperation(value = "Follows topic if user do not already")
    @PostMapping("/{id}/follow")
    public ResponseEntity<StringResponse> subscription(@PathVariable Integer id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userExists = userRepository.findByUsername(username);

        User user = userExists.get();

        Integer userId = user.getId();
        topicService.subscribe(userId, id);

        return ResponseEntity.ok(new StringResponse("You are following " + id));

    }
    
    @ApiOperation(value = "Unfollows topic if user user is following")
    @DeleteMapping("/{id}/unfollow")
    public ResponseEntity<StringResponse> unsubscribe(@PathVariable Integer id) {

             Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
             String username = authentication.getName();
             Optional<User> userExists = userRepository.findByUsername(username);

             User user = userExists.get();

             Integer userId = user.getId();
            topicService.unsubscribeFromTopic(userId, id);

            return ResponseEntity.ok(new StringResponse("You successfully unsubscried from" + id));
        
    }
}
