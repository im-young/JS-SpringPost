package com.example.post.model.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum GenderType {
	MALE("남성"),
	FEMALE("여성");
	
	private final String description;
}
