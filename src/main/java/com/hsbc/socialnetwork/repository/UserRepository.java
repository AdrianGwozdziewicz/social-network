package com.hsbc.socialnetwork.repository;

import com.hsbc.socialnetwork.model.User;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.hsbc.socialnetwork.model.User.newUser;

/**
 * Simple datasource, keeps users in memory
 */
@Component
public class UserRepository {

	private static final Set<User> users = new HashSet<>();

	public User add(final String username) {
		final User newUser = newUser(username);
		users.add(newUser);
		return newUser;
	}

	public Optional<User> findByUsername(final String username) {
		return users.stream().filter(user -> user.getUsername().equals(username)).findAny();
	}

	public Set<User> getAll() {
		return users;
	}
}
