package com.example.post.model.users;

import java.time.LocalDate;

import lombok.Data;

@Data
public class User {
	private Long id; // 회원 정보를 구분하는 ID
	private String username;// 로그안 아이디
	private String password;// 로그인 패스워드
	private String name;// 사용자 이름
	private GenderType gender;
	private LocalDate birthDate;
	private String email;

	

}
