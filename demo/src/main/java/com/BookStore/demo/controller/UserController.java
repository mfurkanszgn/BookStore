package com.BookStore.demo.controller;

import com.BookStore.demo.dto.AuthRequest;
import com.BookStore.demo.dto.CreateUserRequest;
import com.BookStore.demo.entity.User;
import com.BookStore.demo.service.JwtService;
import com.BookStore.demo.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

	private final UserService userService;

	private final JwtService jwtService;

	private final AuthenticationManager authenticationManager;

	public UserController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
		this.userService = userService;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}

	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome";
	}

	@PostMapping("/addNewUser")
	public User addNewUser(@RequestBody CreateUserRequest createUserRequest) {
		return userService.createUser(createUserRequest);
	}

		@PostMapping("/generateToken")
	public String generateToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(authRequest.username());
		}
		throw new UsernameNotFoundException("invalid username or password");
	}

	@GetMapping("/user")
	public String getUserString() {
		return "This is user";
	}

	@GetMapping("/login")
	public String getAdminString() {
		return "This is admin";
	}

}
