package com.BookStore.demo.security;

import com.BookStore.demo.exception.SecurityConfigurationException;
import com.BookStore.demo.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private final JwtAuthFilter jwtAuthFilter;

	private final UserService userService;

	private final PasswordEncoder passwordEncoder;

	public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserService userService, PasswordEncoder passwordEncoder) {
		this.jwtAuthFilter = jwtAuthFilter;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		try {
			http
					.csrf(AbstractHttpConfigurer::disable)
					.authorizeHttpRequests((authorize -> authorize
							.requestMatchers("/auth/addNewUser/**", "/auth/generateToken/**").permitAll()
							.requestMatchers("/book/addBook/**", "/book/deleteBook/**", "/book/updateBook/**")
							.hasRole("ADMIN")
							.requestMatchers("/auth/user/**", "/auth/login/**", "/book/**", "/orders/**")
							.hasAnyRole("USER", "ADMIN")))
					.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
					.authenticationProvider(authenticationProvider())
					.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
			return http.build();
		} catch(Exception e) {
			throw new SecurityConfigurationException(e.getMessage());
		}
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userService);
		authProvider.setPasswordEncoder(passwordEncoder);
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userService);
		authProvider.setPasswordEncoder(passwordEncoder);
		return configuration.getAuthenticationManager();
	}
}
