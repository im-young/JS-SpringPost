package com.example.post.controller;

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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor	// 롬복 생성자 주입 어노테이션 -> 필드 선언만 하면 알아서 생성자 주입 방식으로 생성
@Controller
public class UserController {
	
	@Autowired	// @RequiredArgsConstructor 쓰면 @Autowired 안 써도 됨
	private final UserService userService;
	
	
	// 회원가입 페이지 요청 처리
	@GetMapping(path = "users/register")
	public String register(Model model) {
		UserCreateDto userCreateDto = new UserCreateDto();
		model.addAttribute("userCreateDto", userCreateDto);
		
		return "users/register";
	}
	
	// 회원가입 요청 처리
	@PostMapping(path = "users/register") 
		public String registerUser(
			@Validated @ModelAttribute UserCreateDto userCreateDto,
			// 검증하려는 파라미터 뒤에 써야 함 BindingResult가 먼저 오면 안 됨
			BindingResult bindingResult
			) {
		
		// 유효성 검증이 실패했는지 확인
		if (bindingResult.hasErrors()) {
			log.info("유효성 검증 실패");
			return "users/register";
		}
		
//		if (user.getUsername() == null || 
//				user.getUsername().length() < 4 || 
//				user.getUsername().length() > 20) {
//			log.info("username: {}", user.getUsername());
//			return "redirect:/user/register";
//		}
		
		log.info("user: {}", userCreateDto);
//		User registedUser = userService.registerUser(user);	
//		log.info("registedUser: {}", registedUser);
		
		return "redirect:/";
	}
	
	// 로그인 페이지 이동
	@GetMapping("users/login")
	public String loginPage(Model model) {
		model.addAttribute("userLoginDto", new UserLoginDto());
		return("users/login");
	}
	
	// 로그인 
	@PostMapping("users/login")
	public String login(
			@Validated @ModelAttribute UserLoginDto userLoginDto,
			//@Validated  : 유효값 검증을 할 거다. @ModelAttribute는 같이 사용 되야함
			//( 받는 파라미터(UserLoginDto userLoginDto)를 검정하겠다 :@Validated )
			BindingResult bindingResult,
			//내가 검증하고자 하는 모델이나 리퀘스트파람 뒤에 붙여야 함함
			// -> 실패 했을 때 에러에 대한 것을 확인 하는 것 : 이게 없으면 서버에서 에러가 나는데, 이거 때문에 에러가 안남 -> 예외에 대한 에러를 바인딩리절트가 갖고 잇음 -> 뭘 하는게 아니고 이걸 그낭 갖고 있음 -> 사용자에게 안내는 어떻게 하냐>? (ex.아이디 길이값 검증 실패 
			HttpServletRequest request) {
		
		// 로그인 정보 검증에 실패하면 로그인 페이지로
		if (bindingResult.hasErrors()) { // 에러가 있으면 return 으로 보냄(화면 전환)
			return "users/login";
		}
		
		log.info("user: {}", userLoginDto);
		
		// username에 해당하는 User 객체 탐색
		User findUser = userService.getUserbyUsername(userLoginDto.getUsername());
		log.info("findUser: {}", findUser);
		
		// 사용자가 입력한 username, password 정보가 데이터베이스의 User 정보와 일치하는지 확인(필드 에러 아님 -> bindingResult.reject 사용(에러코드, 에러메세지)를 파라미터로 담아줘야함함) /필드에러는@Validated로 검증해서 별도로 처리할 필요 없고 있는지만 확인하면 됨됨
		if (findUser == null || !findUser.getPassword().equals(userLoginDto.getPassword())) {
			// 로그인 실패 시 로그인 페이지로 리다이렉트
			bindingResult.reject("loginFailed", "아이디 또는 패스워드가 다릅니다.");
			return "/users/login";
		}
		
		// Request 객체에 저장되어 있는 Session 객체를 받아옴
		HttpSession session = request.getSession();
		// session에 로그인 정보 저장
		session.setAttribute("loginUser", findUser);
		
		return "redirect:/";
	}
	
	// 로그아웃
	@GetMapping("users/logout")
	public String logout(HttpSession session) {
//		session.setAttribute("loginUser", null);
		// 세션의 데이터를 모두 삭제
		session.invalidate();
		return "redirect:/";
	}
	
	// 세션 저장 확인
	@ResponseBody
	@GetMapping("loginCheck")
	public String loginCheck(HttpServletRequest request) {
		// Request 객체에 저장되어 있는 Session 객체를 받아옴
		HttpSession session = request.getSession();
		// session에 저장된 loginUsername 정보를 찾음
		String loginUsername = (String) session.getAttribute("loginUsername");
		log.info("loginUsername: {}", loginUsername);
		
		return "ok";
	}
	
}
