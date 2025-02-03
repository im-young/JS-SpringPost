package com.example.post.model.users;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class User {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;			// 회원 정보 구분 ID
	private String username;	// 로그인 아이디
	private String password;	// 로그인 패스워드
	private String name;		// 사용자 이름	
	@Enumerated(EnumType.STRING)
	private GenderType gender;	
	private LocalDate birthDate;
	private String email;
}
