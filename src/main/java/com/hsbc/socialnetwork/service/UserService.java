package com.hsbc.socialnetwork.service;

import com.hsbc.socialnetwork.controller.UserResponse;
import com.hsbc.socialnetwork.exceptions.ApiException;
import com.hsbc.socialnetwork.model.Post;
import com.hsbc.socialnetwork.model.User;
import com.hsbc.socialnetwork.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	/**
	 * gets all users existing in app
	 *
	 * @return set of users
	 */
	public Set<UserResponse> getUsers() {
		return userRepository.getAll().stream()
				.map(UserResponse::mapToResponse)
				.collect(toSet());
	}

	/**
	 * add new post to user
	 * if user not exist a new one is created
	 *
	 * @param username post author
	 * @param message  post content
	 */
	public void addPost(final String username, final String message) {
		final User user = userRepository.findByUsername(username)
				.orElseGet(() -> userRepository.add(username));
		user.addPost(message);
	}

	/**
	 * edit post by username
	 * if user is not an owner, api exception is throws
	 *
	 * @param username author
	 * @param id       post id
	 * @param message  new message
	 */
	public void editPost(final String username, final Integer id, final String message) {
		final User user = getValidatedUser(username);
		final Post post = user.getPosts().stream().filter(postPredicate(id)).findAny()
				.orElseThrow(() -> new ApiException("Post not found"));

		post.update(message);
	}

	/**
	 * delete username's post
	 * if user is not an owner, api exception is throws
	 *
	 * @param username
	 * @param id
	 */
	public void deletePost(final String username, final Integer id) {
		final User user = getValidatedUser(username);
		user.getPosts().removeIf(postPredicate(id));
	}

	/**
	 * gets user wall, all posts created by username
	 * sorted by created date descending
	 *
	 * @param username author
	 * @return set of posts
	 */
	public List<Post> getUserWall(final String username) {
		final User user = getValidatedUser(username);

		return user.getPosts().stream()
				.sorted(comparing(Post::getCreatedDate).reversed())
				.collect(toList());
	}

	/**
	 * gets all posts craeted by user and posts that a user is following
	 * sorted by created date descending
	 *
	 * @param username author
	 * @return list of post
	 */
	public List<Post> getUserTimeline(final String username) {
		final User user = getValidatedUser(username);

		final Stream<Post> followersStream = user.getFollowers().stream().flatMap(f -> f.getPosts().stream());

		return Stream.concat(user.getPosts().stream(), followersStream)
				.sorted(comparing(Post::getCreatedDate).reversed())
				.collect(toList());
	}

	/**
	 * follow user to show others posts
	 *
	 * @param username         - user context
	 * @param usernameToFollow - user to follow
	 */
	public void followUser(final String username, final String usernameToFollow) {
		final User user = getValidatedUser(username);
		final User userToFollow = getValidatedUser(usernameToFollow);

		if (user.equals(userToFollow))
			throw new ApiException("You cannot follow yourself");

		user.follow(userToFollow);
	}

	/**
	 * un-follow user to hide follower post
	 * if user is not following the pointed user an exception is thrown
	 *
	 * @param username - user context
	 * @param follower - user to unfollow
	 */
	public void unFollowUser(final String username, final String follower) {
		final User user = getValidatedUser(username);

		if (user.getFollowers().stream().map(User::getUsername).noneMatch(name -> name.equals(follower))) {
			throw new ApiException(String.format("User %s is not following user %s", username, follower));
		}

		user.unFollow(follower);
	}

	/**
	 * check if user exists in datasourse
	 *
	 * @param username
	 * @return
	 */
	private User getValidatedUser(final String username) {
		return userRepository.findByUsername(username).orElseThrow(() -> new ApiException("User not found"));
	}

	/**
	 * predicate if post exists by id
	 *
	 * @param id
	 * @return
	 */
	private Predicate<Post> postPredicate(final int id) {
		return (Post p) -> p.getId() == id;
	}
}
