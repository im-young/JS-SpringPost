package com.example.post.model.users;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
// 회원 가입을 받기 위한 전용 클래스 : 
/*
 * 만든아유
 * 1. USER는 엔팉로 활용하기 위해서 만듬(데이터 베이스에 저장하려고),
 * 	  USER는 회원가입을 할떄 필요 없는 필드가 있음
 * 
 *  2.validation 때문
 *  회원정보 수정을 할 때도 가입시 필요한 validation 정보가 잇음
 *  => 회원 가입할 때 validation(유효성 검사)와 수정시 필요한 validation이 다름*/
public class UserCreateDto {
	// 유효성 검사 -----
		@NotBlank 
		@Size(min = 4, max = 20)
		//-----
		private String username;
		private String password;
		private String name;
		private GenderType gender;
		@Past // 과거날짜일 경우에만 통과 가능
		@DateTimeFormat(pattern ="yyyy-MM-dd")
		private LocalDate birthDate;
		private String email;
		
		
		// 로그인 체크 
		public User toEntity() { // 바로 엔티티 타입으로 변환 -> 컨트롤러에서 엔티티로 바꾸는 코드를 작성하지 않아도 됨
//			User user = new User();
//			user.setUsername(this.getUsername());
//			user.setPassword(this.getPassword());
//			user.setName(this.getName());
//			user.setBirthDate(this.getBirthDate());
//			user.setGender(this.getGender());
//			user.setEmail(this.getEmail());
//			return user;

			// 빌드업 패턴 ================= 
			// 메서드.메서드. ... (메서드 체인 방식을 통해서 사용)
			//// 모든 필드를 파라미터로 받는 생성자와 아무것도 받지 않는 생성자가 필요함 -> model의 user에 어노테이션 달아줘야함
			
			return User.builder()
					.username(this.username)
					.password(this. password)
					.name(this.name)
					.gender(this.gender)
					.birthDate(this.birthDate)
					.email(this.email)
					.build();
			}
}
