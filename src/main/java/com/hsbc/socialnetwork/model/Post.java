package com.hsbc.socialnetwork.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@EqualsAndHashCode
@ToString
public class Post {

	private static int counter = 0;

	private int id;
	private String message;
	private User author;
	private LocalDateTime createdDate;

	private Post(final User author, final String message) {
		this.id = ++counter;
		this.message = message;
		this.author = author;
		this.createdDate = LocalDateTime.now();
	}

	public static Post newPost(final User user, final String postContent) {
		return new Post(user, postContent);
	}

	public Post update(final String message) {
		this.message = message;
		return this;
	}
}
