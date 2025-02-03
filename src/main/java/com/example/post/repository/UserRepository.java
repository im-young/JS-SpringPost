package com.example.post.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.post.model.users.User;

//각각의 엔티티마다 레포지토리가 있어야함			//구현체는 jpa가 알아서 만들어 줌
public interface UserRepository extends JpaRepository<User, Long>{
	 												// T: 어떤 엔티티 타입을 관리 할꺼냐, id : user에 있는 id의 타입
// 	JpaRepository 에 기본 메서드가 있음 -> 그래서 userService에서 오류가 안나는 메서드가 있음
	/*자주 사용하는 메서드
	 * * Spring Data Jap의 CRUD
	 * Create : save(엔티티 객체)
	 * Read   : findById(엔티티 객체의 id) , findAll()
	 * Update : 없음 (영속성 컨텍스에서 변경 감지를 통해 업데이트 하기 때문)
	 * Delete : delete(엔티티 객체(아이디 아님)) 
	 *  
	 * */
	
	//username으로 회원정보 조회:id가 아니라 username으로 찾는 메서드 -> 쿼리 메서드(엔티티의 정보를 읽어와서 자동으로 완성 가능하게 만들어줌)
		// 조회는 find를 사용함, 조건이 없으면 All, 있으면 By
	User findByUsername(String username); // username은 유니크 한거- >리턴은 없거나 하나 
	
}
