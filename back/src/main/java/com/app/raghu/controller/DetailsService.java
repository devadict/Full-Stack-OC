package com.app.raghu.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.raghu.entity.User;
import com.app.raghu.repository.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/user")
@Api(value = "User ressources endpoint", description = "Shows the users info")
@Service
public class DetailsService {
    @Autowired
    private UserRepository userRepository;


	@ApiOperation(value = "Get yser user info from its Id")
    @GetMapping("/{id}")
	public ResponseEntity<User> me(@PathVariable Integer id, Principal p) {
		String username = p.getName();

		Integer userId = userRepository.findByUsername(username).get().getId();

		id = userId;

		User user = userRepository.findById(id).get();
		

		return ResponseEntity.ok(user);
	}
}
