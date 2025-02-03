package com.example.post.model.users;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

//@Entity
@Data
// 회원가입을 받기 위한 전용 클래스
public class UserCreateDto {
	@NotBlank
	@Size(min = 4, max = 20)
	private String username;	// 로그인 아이디
	private String password;	// 로그인 패스워드
	private String name;	
	private GenderType gender;
	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;
	private String email;
}
