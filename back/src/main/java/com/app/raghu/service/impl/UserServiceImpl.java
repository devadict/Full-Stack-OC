package com.app.raghu.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.raghu.entity.Topic;
import com.app.raghu.entity.User;
import com.app.raghu.repository.TopicRepository;
import com.app.raghu.repository.UserRepository;
import com.app.raghu.service.IUserService;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;

	@Autowired
	private TopicRepository topicRepository;

	public User saveUser(User user) {

		user.setPassword(pwdEncoder.encode(user.getPassword()));
		user.setUpdated_at(LocalDateTime.now());
		user.setUsername(user.getEmail());
		return repository.save(user);
	}

	public User updateUser(Integer userId, String name, String email) {
		User existingUser = repository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));


		existingUser.setName(name);
		existingUser.setEmail(email);

    	return repository.save(existingUser);
	}

	public User findByUsername(String username) {
		Optional<User> user = repository.findByUsername(username);
		if (user.isPresent())
			return user.get();
		return null;
	}

	public User findUserById(Integer id) {
		Optional<User> user = repository.findById(id);
		if (user.isPresent())
			return user.get();
		return null;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException(
					new StringBuffer().append("User name ").append(username).append(" not found!").toString());

		List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role))
				.collect(Collectors.toList());

		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
	}

	
	
}
