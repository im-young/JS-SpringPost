package com.example.post.service;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.post.model.users.User;
import com.example.post.repository.UserRepository;

@Service
public class UserService {
	/*
	 * 의존성 주입 방법
	 * 1. 필드 주입
	 * 2. 생성자 주입
	 * 3. 세터 주입
	 *
	 * Spring Data Jpa의 CRUD
	 * Create : save(엔티티 객체)
	 * Read : findById(엔티티 객체의 아이디), findAll()
	 * Update : 없음(영속성 컨텍스트에서 변경 감지를 통해 업데이트하므로)
	 * Delete : delete(엔티티 객체)
	 * */
//	@Autowired // 필드 주입
	private UserRepository userRepository;
	
//	@Autowired // 생성자 주입((파라미터가 1개일 때는 @Autowired 생략 가능)
	// 테스트를 할 때는 생성자 주입 방식이 유용하다(mock 객체를 생성) (추천!)
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Autowired // 세터 주입 
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	// 사용자 등록
	public User registerUser(User user) {
		// 중복된 이메일이 있으면 등록하지 않음
		return userRepository.save(user);
	}
	
	// ID로 사용자 조회
	public User getUserById(Long id) {
		Optional<User> result = userRepository.findById(id);
		if (result.isPresent()) {
			return result.get();
		}
		throw new RuntimeErrorException(null, "회원정보가 없습니다.");
	}
	
	// 전체 회원정보 조회
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	// username으로 회원정보 조회
	public User getUserbyUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
