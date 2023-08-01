package com.app.raghu.service;

import com.app.raghu.entity.User;

public interface IUserService {

	public User saveUser(User user);

	public User findByUsername(String username);

	public User updateUser(Integer userId, String name, String email);
	
	public User findUserById(Integer id);
}
