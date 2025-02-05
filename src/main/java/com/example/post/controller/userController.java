package com.example.post.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.post.model.users.User;
import com.example.post.model.users.UserCreateDto;
import com.example.post.model.users.UserLoginDto;
import com.example.post.repository.UserRepository;
import com.example.post.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

//import com.example.SpringBoot_WebMvc.controller.RequestParamController;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.constant.DefaultValue;

@RequiredArgsConstructor // 생성자 주입 롬복 어노테이션 -> 필드에 선언하면 따로 생성자를 안 만들어도 됨

@Controller
@Slf4j
public class userController {
// 회원가입 페이지 요청 처리
//		@Autowired
	private final UserService userService; // 이 때 무조건 final 붙여야함

// // 회원가입 페이지 요청 처리
//	@GetMapping("users/register")
//	public String register() {
//		
//		return "users/register";
//	}
// 회원가입 요청 처리
	// 방법1 ---------------------
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
	// 방법2.class로 불러오기 ---------------------------------------
//	@PostMapping(path="users/register") // register_v3에서 register로 변경
//	public String regiter_v3_class(
//			@ModelAttribute User user) {
//		
////		log.info("email:{}",user);// 확인용
////		log.info("name:{}",user.getName());
//		// 
//		User registedUser = userService.registerUser(user); // user 생성
//		log.info("registedUser:{}", registedUser); // 확인용
//
//		
//		return "redirect:/"; //index페이지로 이동
//	}

//회원 가입 여청 처리(V5_day 0124 : 유효성 검사) --------------------
	// 회원가입 페이지 요청 처리
	// 회원가입 페이지 요청 처리
	@GetMapping("users/register")
	public String register(Model model) {
		UserCreateDto userCreateDto = new UserCreateDto();
		model.addAttribute("userCreateDto", userCreateDto);
		return "users/register";
	}

	// 회원가입 작업
	@PostMapping(path = "users/register")
	public String registerUser(

			@Validated // 유효성 체크 : @ModelAttribute UserCreateDto userCreateDto서 값을 갖고 올 때 , 이 어노테이션이
						// 있으면 유효성 검사를 해야지 하는 것 (파라미터로 유효성 검증하는 것)
			// @Validate 도 가능
			@ModelAttribute UserCreateDto userCreateDto, // html의 값을 여기에 담음 -> userdto의 변수명을 사용 할 수 있음
			BindingResult bindingResult // 위치는 validation 다음에 위치 , 유효성 검증에서 오류나도 페이지가 넘어감

	) {
		// 유효성 검증이 실패 했는지 확인
		if (bindingResult.hasErrors()) {
			log.info("유효성 검증 실패");
			return "users/register";
		}
//		User registedUser = userService.registerUser(user); // user 생성

		// username 중복 확인 (Day 0203)
		if (userService.getUserbyUsername(userCreateDto.getUsername()) != null) {
			// 이미 사용중인 username이 있다.
			// 에러코드는 errors.properties 파일에 정의된 에러 코드와 메세지를 사용한다.
			// bindingResult.reject("duplicateUsername", "이미 사용중인 아이디 입니다.");
			bindingResult.reject("duplicateUsername.username");
			return "users/register";
		}
			// userCreateDto -> user타입으로 변환 ( 위의 코드(username에 해당하는 User 객체 탐색)랑 같이 동작함)
			User registedUser = userService.registerUser(userCreateDto.toEntity());
		      log.info("registedUser: {}", registedUser);
		      
		      return "redirect:/";
	}

// =================== Day 0121 = 세션(로그인,로그아웃 : 사용자 정보 저장) ===============================
//// 로그인 페이지 이동
//	 // 회원가입 페이지 요청 처리
//		@GetMapping("users/login")
//		public String login() {
//			
//			return "users/login";
//		}
////		// 로그인
//		@PostMapping("users/login")
//		public String login(
//		@ModelAttribute User user,
//		//사용자가 request를 보낼 때 값을 갖고 있는것?(동영상 0121,10:47분쯤)
//		//reqest객체안에 세션이 들어있음
//		HttpServletRequest request) {
//		//아이디, 패스워드 잘 갖고 왔는지 확인 
//		log.info("user: {}", user);
//		//username에 해당하는  User 객체를 찾는다.
//		User findUser = userService.getUserbyUsername(user.getUsername());
//		log.info("findUser:{}",findUser);
//		
//		//사용자가 입력한 username, password 정보가 데이터베이스의 user 정보와 일치하는지 확인
//		if(findUser == null || !findUser.getPassword().equals(user.getPassword())) {
//			// 로그인 실패 시 로그인 페이즈로 리다이렉트
//			log.info("로그인 실패");
//			return "redirect:/users/login";
//		}log.info("로그인 성공");
//		
//		//request객체에 저장 되어 있는 Session객채를 받아온다
//		HttpSession session = request.getSession();
//		//session에 로그인 정보를 저장
//		session.setAttribute("loginUser", findUser);
//							// 변수명(name) , 값(object type)
//		log.info("세션에 저장된 loginUser: {}", session.getAttribute("loginUser"));
//
//		return "redirect:/";
//
//				}

	// -------- 로그인 유효성 ㅇ검사
	// 로그인 페이지 이동
	@GetMapping("users/login")
	public String loginForm(Model model) {
		model.addAttribute("userLoginDto", new UserLoginDto());
		return "users/login";

	}

//		//로그인 유효성 검사
	@PostMapping("users/login")
	public String login(@Validated @ModelAttribute UserLoginDto userLoginDto,
			// @Validated : 유효값 검증을 할 거다. @ModelAttribute는 같이 사용 되야함
			// ( 받는 파라미터(UserLoginDto userLoginDto)를 검정하겠다 :@Validated )
			BindingResult bindingResult,
			// 내가 검증하고자 하는 모델이나 리퀘스트파람 뒤에 붙여야 함함
			// -> 실패 했을 때 에러에 대한 것을 확인 하는 것 : 이게 없으면 서버에서 에러가 나는데, 이거 때문에 에러가 안남 -> 예외에 대한
			// 에러를 바인딩리절트가 갖고 잇음 -> 뭘 하는게 아니고 이걸 그낭 갖고 있음 -> 사용자에게 안내는 어떻게 하냐? (ex.아이디 길이값
			// 검증 실패) -> 메스드에서 if로 검증해서 html로 오류값 보내기(filters.global)
			HttpServletRequest request, // 세션 갖고오기
			@RequestParam(name = "redirectURL", defaultValue =  "/") String redirectURL ) { //Day0205 : 로그인 후 사용자가 원래 가려고 했던 페이지로 이동 시키기 위해 리다이렉트 url정보를 받아 오는 것.
												// redirectURL 이게 없으면 오류나서 이거 갖고 오기
		// 로그인 정보 검증에 실패하면 로그인 페이지로
		if (bindingResult.hasErrors()) { // 에러가 있으면 return 으로 보냄(화면 전환)
			return "users/login";
		}
		;
		log.info("user: {}", userLoginDto);

//		 username에 해당하는 User 객체 탐색
		User findUser = userService.getUserbyUsername(userLoginDto.getUsername());
		log.info("findUser: {}", findUser);
		// 사용자가 입력한 username, password 정보가 데이터베이스의 User 정보와 일치하는지 확인
		if (findUser == null || !findUser.getPassword().equals(userLoginDto.getPassword())) {
			// 로그인 실패 시 로그인 페이지로 리다이렉트
			bindingResult.reject("loginFailed", "아이디 또는 패스워드가 다릅니다.");
			return "/users/login";
		}		;
	// Day0205 	
	//Request 객체에 저장되어 있는 Session 객체를 받아온다.
		HttpSession session = request.getSession();
		//session에 로그인 정보를 저장한다
		session.setAttribute("loginUser", findUser);
		return "redirect:" + redirectURL	;
	}
		
		

	// 로그아웃===================
	@GetMapping("users/logout")
	public String logout(HttpSession session) {
		// 방법1 : loginUser 만 null처리
//			session.setAttribute("loginUser", null);
		// 방법2 : 세션의 데이터를 모드 삭제한다.
		session.invalidate();
		return "redirect:/";
	}

	// 세션 저장 확인
	@ResponseBody // return 값을 바디에 보여줌
	@GetMapping("loginCheck")
	public String loginCheck(HttpServletRequest request) {
		// loginUsername 이 있으면 로그인이 된것, 없으면 로그인 저장 안 된것
		// request 객체에 저장돼 있는 session 객채를 받아온다.
		HttpSession session = request.getSession();
		// session에 저장된 loginUsername 정보를 찾는다.
		session.getAttribute("loginUsername");
		// return 타임은 object -> 벗 우리가 위에 저장한 username은 string타입 => 형변환 시켜줘야함
		// 형변환
		String loginUsername = (String) session.getAttribute("loginUsername");
		log.info("loginUsername:{}", loginUsername);

		return "ok";

	}

	// 회원정보 수정
	@GetMapping("users/updateuser")
	public String updateUser() {

		return "users/updateuser";
	}

	@PostMapping("users/updateuser")
	public String updateUser1(@ModelAttribute User user) {
		Long id = user.getId();
		String password = user.getPassword();
		String email = user.getEmail();
		userService.updateUser(id, password, email);
		return "users/index";
	}
}
