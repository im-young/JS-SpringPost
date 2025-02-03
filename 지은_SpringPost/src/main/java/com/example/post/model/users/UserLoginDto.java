package com.example.post.model.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

// 로그인 전용 클래스
@Data
public class UserLoginDto {
	@NotBlank // 이게 있다고 항상 유효성 검사를 하는 것은 아님 -> 컨트롤러에 받는 파라미터(UserLoginDto userLoginDto)를 검정하겠다 :@Validated 를 있어야함함
	@Size(min = 4, max = 20, message = "아이디는 4글자 이상 20글자 이하로 입력해 주세요.")
	private String username;
	@NotBlank
	@Size(min = 4, max = 20)
	private String password;
}
