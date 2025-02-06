package com.example.post.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
// 로그인 체크와 똑같은 작동하는 인터셉터
@Slf4j
public class LoginCheckIntercptor implements HandlerInterceptor{
// `로그인 체크`는 인터셉터 전에 실행되야 하기 때문에 `pre핸들러` 사용
	@Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
	log.info("로그인 체크 인터셉터 실행");
	// 
	String requestURI = request.getRequestURI();
	//  세션정보 가져오기 
	HttpSession session = request.getSession(false); // 필터에서는 여기서 형변환을 해줘야 했는데 여기서는 안해줘도 됨. 애초에 받는 파라미터가 http서블릿리스폰스라서 -> 여기에 들어 있는 세션을 가지고 오면됨
	if(session == null || session.getAttribute("loginUser") == null) {
		// 로그인을 하지 않은 경우 -> 로그인 페이지로 리다이렉트
		log.info("로그인 하지 않은 사용자 입니다.");
		//Day0205
		response.sendRedirect("/users/login?redirectURL=" + requestURI); // 원래 사용자가 갈려고 했던 url 값을 붙여서 리다이렉트 보내기 => 로그인 후 사용자가 가려고 했던 페이지로 이동 함
		return false;
	}
	
	// 로그인이 된 경우
	
	return true;
}
}
