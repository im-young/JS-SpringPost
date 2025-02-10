package com.example.post.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.post.model.posts.Post;
import com.example.post.model.users.User;
import com.example.post.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
public class UserService {
	/*
	 * # 의존성 주입 방법 1. 필드 주입 : 어노테이션 사용 2. 생성자 주입 : 쓰기는 가장 편함 3. 세터 주입
	 * 
	 * Spring Data Jap의 CRUD -> repository에 메서드를 만들지 않아도 오류안남 Create : save(엔티티 객체)
	 * Read : findById(엔티티 객체의 id) , findAll() Update : 없음 (영속성 컨텍스에서 변경 감지를 통해 업데이트
	 * 하기 때문) Delete : delete(엔티티 객체(아이디 아님))
	 * 
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
		return userRepository.save(user); // 메서드가 없는데 오류가 안나는 것은 이미 약속이 되어 있다. -> jpa레파지토리에 메서드가 있음
	}

	// id로 사용자 조회
	public User findUser(Long id) {
		Optional<User> result = userRepository.findById(id); // 이건 생성하는게 아니라 갖고 오는것 -> new 붙이면 안됨!
		if (result.isPresent()) {
			return result.get();
		}
//		throw new RuntimeException("회원 정보가 없습니다.");
		return result.get();
	}

	// 모든 사용자 조회
	public List<User> AllFindUser() {
		List<User> users = userRepository.findAll();
		return users;
	}

	// username으로 회원정보 조회
	public User getUserbyUsername(String username) {
		User user = userRepository.findByUsername(username);

		// user가 null 이면 예외 발생 -> 컨트롤러 단에서 null 일때의 코드가 필요 없음
//		if (user == null) {
//			throw new NoSuchElementException("사용자가 존재하지 않습니다.");
//		}
		return user;
	}

	public User updateUser(Long id, String password, String email) {
		Optional<User> findUser = userRepository.findById(id); // 이건 생성하는게 아니라 갖고 오는것 -> new 붙이면 안됨!
		User user = findUser.get();
		user.setPassword(password);
		user.setEmail(email);
		return user;
	}
}
