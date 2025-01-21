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

import com.example.post.model.User;
import com.example.post.repository.UserRepository;
import com.example.post.service.UserService;

import lombok.RequiredArgsConstructor;

//import com.example.SpringBoot_WebMvc.controller.RequestParamController;

import lombok.extern.slf4j.Slf4j;
@RequiredArgsConstructor // 생성자 주입 롬복 어노테이션 -> 필드에 선언하면 따로 생성자를 안 만들어도 됨

@Controller
@Slf4j
public class userController2 {
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
	@PostMapping(path="register")//register_v3 ->register 로 바뀜
	public String regiter_v3_class(
			@ModelAttribute User user) {
		
//		log.info("email:{}",user);// 확인용
//		log.info("name:{}",user.getName());
		// 
		User registedUser = userService.registerUser(user);
//		log.info("registedUser:{}", registedUser); // 확인용

		
		return "register_success";
	}
	
	//id로 회원정보 조회하기:
	/*url 로 요청하기
	 *ex)/user-details/{사용자ID}->정보를 조회하여 (모델에 담고)-> user_detail.html을 보여주면 된다.
	 *user-details/1 이라고 창에 입력하면 1번 회원 조회
	 * */
	@GetMapping(path = "user-detail/{id}")//{변하는 값,경로변수로 사용-> 프론트에서 값을 가지고 오기}
	public String userDetail(
			@PathVariable(name = "id") Long id, // path에서 지정한 변수와 동일해야함 
			Model model
			){
		User user = userService.findUser(id);
//		log.info("findID:{}", id);	//확인용	
		model.addAttribute("user",user);
		return "user_detail";
	}
	
// 회원 목록 조회(user_list) & 링크 클릭하면 detail로 갈수 있도록 만들기(방법1 , path valiable)
		@GetMapping(path = "user-list")
		public String user_list(Model model ){ // model: 회원 정보를 담아서 프론트로 넘겨줌
			List<User> users = userService.AllFindUser();
			log.info("firstUser:{}", users.get(0));		
			model.addAttribute("users",users);

			return "user_list";
		}
	
// 방법2
//		@GetMapping("user-details")
//		public String userDetailsV1(
//		@RequestParam(name = "id") Long id,
//		Model model) {
//
//		User user = userService.getUserById(id);
//		// 검색한 User 정보를 Model에 담는다.
//		model.addAttribute("user", user);
//
//		return "user_detail";
//
//		}
		


}
