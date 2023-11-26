package com.BookStore.demo.service;

import com.BookStore.demo.dto.CreateUserRequest;
import com.BookStore.demo.entity.User;
import com.BookStore.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;

	private final BCryptPasswordEncoder passwordEncoders;

	public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoders) {
		this.userRepository = userRepository;
		this.passwordEncoders = passwordEncoders;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				return userRepository.findByUsername(username)
						.orElseThrow(() -> new UsernameNotFoundException("User not found"));

	}

	public User createUser(CreateUserRequest createUserRequest) {
		User newUser = User.builder()
				.name(createUserRequest.name())
				.username(createUserRequest.username())
				.password(passwordEncoders.encode(createUserRequest.password()))
				.authorities(createUserRequest.authorities())
				.accountNonExpired(true)
				.credentialsNonExpired(true)
				.isEnabled(true)
				.accountNonLocked(true)
				.build();
		return userRepository.save(newUser);
	}
}