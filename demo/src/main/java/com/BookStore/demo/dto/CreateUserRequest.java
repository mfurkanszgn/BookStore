package com.BookStore.demo.dto;

import com.BookStore.demo.entity.Role;
import lombok.Builder;

import java.util.Set;

@Builder
public record CreateUserRequest(
		String name,
		String username,
		String password,
		Set<Role> authorities
) {}