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

//	@Bean : db연결 했을 때 주석처리 안하면 계속 생성되서 사용자가 겹침
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
