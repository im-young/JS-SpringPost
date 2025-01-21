package com.example.post.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.post.model.User;

@Repository
public class UserRepository {
	private static Long sequence = 0L; // Id 자동 증가를 위한 시퀀스
	private final Map<Long, User> store = new HashMap<>(); // 메모리 저장소

	//User 저장
	public User save(User user) {
		user.setId(++sequence); // id 자동 할당
		store.put(user.getId(),user);
		return user;
	}

	//id 조회
	public Optional<User> findById(Long id){
		return Optional.ofNullable(store.get(id));
	}
	//Optonal : 클래스 명 , 객체를 한번 감싸는 것-> null,user이 들어 있을 때 null일 경우와 null 아닐 경우를 판단함 ex) user을 받을 수 있음,
	
	// 모든 User 조회
	public List<User> findAll(){
		return new ArrayList<>(store.values());
	}
}
