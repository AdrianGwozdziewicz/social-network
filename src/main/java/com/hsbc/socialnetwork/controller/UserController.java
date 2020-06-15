package com.hsbc.socialnetwork.controller;

import com.hsbc.socialnetwork.model.Post;
import com.hsbc.socialnetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;


@RestController
@RequiredArgsConstructor
@RequestMapping("users")
@Validated
// uncomment when serving on different post or create a proxy
//@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true", value = "*")
public class UserController {

	private final UserService userService;

	@GetMapping
	Set<UserResponse> getUsers() {
		return userService.getUsers();
	}

	@GetMapping("{username}/wall")
	List<Post> getUserWall(@PathVariable final String username) {
		return userService.getUserWall(username);
	}


	@GetMapping("{username}/timeline")
	List<Post> getUserTimeline(@PathVariable final String username) {
		return userService.getUserTimeline(username);
	}


	@PostMapping("{username}/posts")
	void addPost(@PathVariable final String username,
				 @RequestBody @Size(min = 1, max = 140) final String message) {
		userService.addPost(username, message);
	}

	@PutMapping("{username}/posts/{postId}")
	void editPost(@PathVariable final String username,
				  @PathVariable final Integer postId,
				  @RequestBody @Size(min = 1, max = 140) final String message) {
		userService.editPost(username, postId, message);
	}

	@DeleteMapping("{username}/posts/{postId}")
	void deletePost(@PathVariable final String username,
					@PathVariable final Integer postId) {
		userService.deletePost(username, postId);
	}


	@PostMapping("{username}/follow/{follower}")
	void followUser(@PathVariable final String username,
					@PathVariable final String follower) {
		userService.followUser(username, follower);
	}

	@PostMapping("{username}/un-follow/{follower}")
	void unFollowUser(@PathVariable final String username,
					  @PathVariable final String follower) {
		userService.unFollowUser(username, follower);
	}


}
