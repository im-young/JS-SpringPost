package com.example.post.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.post.model.User;
import com.example.post.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
public class UserService {
	/*
	 * # 의존성 주입 방법 1. 필드 주입 : 어노테이션 사용 2. 생성자 주입 : 쓰기는 가장 편함 3. 세터 주입
	 */

	// 방법 1 : 필드 주입
	@Autowired
	private UserRepository userRepository;

//방법 2 : 생성자 주입 : 불변 객체를 받을 수 있음 벗 세터는 계속 받아올수 있음 -> 세터를 주로 자주 사용함
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// 방법 3 :
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

//-------------------------- 
	// 사용자 등록
	public User registerUser(User user) {
		return userRepository.save(user);
	}

	// id로 사용자 조회
	public User findUser(Long id) {
		Optional<User> result =  userRepository.findById(id); // 이건 생성하는게 아니라 갖고 오는것 -> new 붙이면 안됨!
		if (result.isPresent()) {
			return result.get();
		}
//		throw new RuntimeException("회원 정보가 없습니다.");
		return result.get();
	}
	
	// 모든 사용자 조회
	 public List<User> AllFindUser() {
		 List<User> users =  userRepository.findAll();
		 return users;
	 }

}
