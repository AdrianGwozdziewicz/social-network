package com.hsbc.socialnetwork.controller;

import com.hsbc.socialnetwork.model.User;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Builder
@Data
public class UserResponse {
	private String username;
	private Set<String> followerNames;

	public static UserResponse mapToResponse(final User user) {
		return builder()
				.username(user.getUsername())
				.followerNames(user.getFollowers().stream().map(User::getUsername).collect(toSet()))
				.build();
	}
}
