package com.app.raghu.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.raghu.dto.request.RegisterRequest;
import com.app.raghu.dto.request.UserUpdateRequest;
import com.app.raghu.dto.response.StringResponse;
import com.app.raghu.entity.User;
import com.app.raghu.entity.UserRequest;
import com.app.raghu.entity.UserResponse;
import com.app.raghu.repository.UserRepository;
import com.app.raghu.service.IUserService;
import com.app.raghu.util.JwtUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Authentication REST Endpoint", description = "Allows the authentication of an user")
@RequestMapping("/api/auth")
@Service
public class UserRestController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder pwdEncoder;

	@Autowired
	private IUserService service;

	@Autowired
	private JwtUtil jwtUtil;

	@ApiOperation(value = "Registers the user if email does not exists already")
	@PostMapping("/register")
	public ResponseEntity<UserResponse> saveUser(@RequestBody RegisterRequest registerRequest) {
		Optional<User> userExists = userRepository.findByUsername(registerRequest.getEmail());
		
		if (userExists.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new UserResponse("Username already exists"));
		}

		User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getEmail());
        user.setPassword(pwdEncoder.encode(registerRequest.getPassword()));
		userRepository.save(user);

		String token = jwtUtil.generateToken(user.getUsername());
		

		return ResponseEntity.ok(new UserResponse(token));
	}

	@ApiOperation("Logs the user in if credentials are good")
	@PostMapping("/login")
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest userRequest)
	{

		String username = userRequest.getEmail();
		try {

			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
					username, 
					userRequest.getPassword()
				)
			);
		} catch (AuthenticationException e)  {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		String token = jwtUtil.generateToken(username);
		

		return ResponseEntity.ok(new UserResponse(token));
	}
	

	@ApiOperation(value = "Retrieve users informations")
	@GetMapping("/me")
	public ResponseEntity<Optional<User>> me(Principal p) {
		String username = p.getName();

		Optional<User> user = userRepository.findByUsername(username);

		if (user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(user);
	}

	@PutMapping("/{id}")
    public ResponseEntity<StringResponse> updateUser(@PathVariable("id") Integer userId, @RequestBody UserUpdateRequest request) {
        service.updateUser(userId, request.getName(), request.getEmail());
        return ResponseEntity.ok(new StringResponse("You updated user successfully"));
    }

}
