package com.example.post.model.users;

import java.time.LocalDate;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.example.post.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class WebConfig {
	
	private final UserRepository userRepository;

	@Bean
	public User createUser() {
	User user = new User();
	user.setUsername("aaa");
	user.setPassword("111");
	user.setName("유저1");
	user.setGender(GenderType.MALE);
	user.setBirthDate(LocalDate.now());
	user.setEmail("user1@gmail.com");
	userRepository. save(user);

	return user;


}
}
