package com.hsbc.socialnetwork.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

import static com.hsbc.socialnetwork.model.Post.newPost;

@Getter
@EqualsAndHashCode
@ToString
public class User {

	private String username;

	@JsonIgnore
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Post> posts = new HashSet<>();

	@JsonIgnore
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<User> followers = new HashSet<>();

	private User(final String username) {
		this.username = username;
	}

	public static User newUser(final String username) {
		return new User(username);
	}

	public void addPost(final String message) {
		posts.add(newPost(this, message));
	}

	public void follow(final User userToFollow) {
		this.followers.add(userToFollow);
	}

	public void unFollow(final String follower) {
		this.followers.removeIf(user -> user.username.equals(follower));
	}
}