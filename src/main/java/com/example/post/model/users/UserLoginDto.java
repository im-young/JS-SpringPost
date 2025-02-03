package com.example.post.model.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
// 로그인 전용 클래스
public class UserLoginDto {
	@NotBlank
	@Size(min=4, max=20, message="아이디는 4자리 이상 20자리 이하로 입력해 주세요")
	private String username;
	@NotBlank
	@Size(min=4, max=20)
	private String password;
}
