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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Setter
//------ 빌드업 패턴---------
// 모든 필드를 파라미터로 받는 생성자와 아무것도 받지 않는 생성자가 필요함
@Builder
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자
@NoArgsConstructor// 기본 생성자

public class User {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) //프라이머리 키값을 안쓰기 위해서? 
	private Long id; // 회원 정보를 구분하는 ID
	
	// 유효성 검사 -----
	@NotBlank 
	@Size(min = 4, max = 20)
	//-----
	private String username;// 로그안 아이디
	private String password;// 로그인 패스워드
	private String name;// 사용자 이름
	
	@Enumerated(EnumType.STRING) // enum 파일(기본값이 숫자)의 값을 string으로 받기 위해서
	private GenderType gender;
	
	@Past // 과거날짜일 경우에만 통과 가능
	private LocalDate birthDate;
	private String email;

	
	// 빌드업 패턴 ==============================
				// 모든 필드를 파라미터로 받는 생성자와 아무것도 받지 않는 생성자가 필요함
	

}
