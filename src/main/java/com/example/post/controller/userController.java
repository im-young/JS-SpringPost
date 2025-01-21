package com.example.post.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.post.model.User;
import com.example.post.repository.UserRepository;
import com.example.post.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

//import com.example.SpringBoot_WebMvc.controller.RequestParamController;

import lombok.extern.slf4j.Slf4j;
@RequiredArgsConstructor // 생성자 주입 롬복 어노테이션 -> 필드에 선언하면 따로 생성자를 안 만들어도 됨

@Controller
@Slf4j
public class userController {
// 회원가입 페이지 요청 처리
//		@Autowired
		private final UserService userService; // 이 때 무조건 final 붙여야함
		
 // 회원가입 페이지 요청 처리
	@GetMapping("register")
	public String register() {
		
		return "register";
		
	}
// 회원가입 요청 처리
	//방법1
//	@PostMapping(path="register_v3")
//	public String register_v3(
//			
//			@RequestParam(name = "name") String name,
//			@RequestParam(name = "email") String email
//			) {
//
//	log.info("name:{}",name);
//	log.info("email:{}",email);
//		
//		return "register_success";
//	}
	//방법2.class로 불러오기
	@PostMapping(path="register") // register_v3에서 register로 변경
	public String regiter_v3_class(
			@ModelAttribute User user) {
		
//		log.info("email:{}",user);// 확인용
//		log.info("name:{}",user.getName());
		// 
		User registedUser = userService.registerUser(user);
//		log.info("registedUser:{}", registedUser); // 확인용

		
		return "register_success";
	}
	
	
		
// =================== Day 0121 = 세션(로그인) ===============================
// 로그인 페이지 이동
	
		// 로그인
		@GetMapping("login")
		public String login(
		@RequestParam(name = "username") String username,
		@RequestParam(name = "password") String password,
		//사용자가 request를 보낼 때 값을 갖고 있는것?(동영상 0121,10:47분쯤)
		//reqest객체안에 세션이 들어있음
		HttpServletRequest request) {
		//아이디, 패스워드 잘 갖고 왔는지 확인 
		log.info("username: {}", username); // 로그인 아이디
		log.info("password:{}", password); // 로그인 패스워드
	
		//request객체에 저장 되어 있는 Session객채를 받아온다
		HttpSession session = request.getSession();
		//session에 로그인 정보를 저장
		session.setAttribute("loginUsername", username);
							// 변수명(name) , 값(object)
		return "redirect:/";

				}
		
		//세션 저장 확인
		@ResponseBody // return 값을 바디에 보여줌
		@GetMapping("loginCheck")
		public String loginCheck(
				HttpServletRequest request) {
			//loginUsername 이 있으면 로그인이 된것, 없으면 로그인 저장 안 된것
			//request 객체에 저장돼 있는 session 객채를 받아온다.
			HttpSession session = request.getSession();
			//session에 저장된 loginUsername 정보를 찾는다.
			session.getAttribute("loginUsername"); 
			//return 타임은 object -> 벗 우리가 위에 저장한 username은 string타입 => 형변환 시켜줘야함
				//형변환
			String loginUsername = (String)session.getAttribute("loginUsername");
			log.info("loginUsername:{}",loginUsername);
			
			return "ok";
					
		}
}
