package com.example.post.filter;
// 필터는 filter 인터페이스를 구현한다.

import java.io.IOException;

import org.springframework.util.PatternMatchUtils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

// 이게 있으면 컨트롤러에서 로그인을 했는지 안했는지 확인하지 않아도 된다.
@Slf4j
public class LoginCheckFilter implements Filter {
	// 로그인이 필요하지 않은 경로(로그인 인증이 필요 없는 경로들)
	private static final String [] whitelist = {
			"/",
			"/users/register",
			"/users/login",
			"/users/logout"
	};
	// 화이트리스트의 경우에는 인증 체크를 하지 않는다.
	public boolean isLongInCheckPath(String requestURI) {
		return !PatternMatchUtils.simpleMatch(whitelist, requestURI); // 화이트 리스트에 해당햐나를 찾는것 -> true면 로그인 체크가 필요 없다는 뜻 -> not(!)을 붙여줌 -> 화이트리스티에 해당되지 않으면 true를 리턴함
	}
	 // 필터 정의
	@Override
	public void doFilter(ServletRequest request, // 요청이 들어왔을 때 생기는 객체(요청에 대한 정보를 담고 있는 객체)
			ServletResponse response, // 응답할 때 사용 되는 객체(응답에 대한 정보를 담고 있는 객체)
			FilterChain chain) //실행정보를 갖고 있음.  필터들이 여러개가 순차적으로 진행 될떄 (체인형식으로 (맞물려 있는 형식, 필터들의 결합)) 필터에 대한 체인 정보
			throws IOException, ServletException {
	
	
		// TODO Auto-generated method stub
		log.info("logincheck필터 실향");// 새로고침 하면 이 로그 작성됨(이게 실행은 됨) -> 화면이 안나옴 (필터의 실행 순서에서 그 다음에 할일을 지정하지 않아서(리턴이
								// 없어서) 서블릿,컨트롤러까지 못감)-> 필터를 통과 시켜 줘야함
		
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		// 사용자가 요청한 URL 정보(로그인이 필요한 요청인지 확인)
		String requestURI = httpRequest.getRequestURI();
		
		try {
			log.info("로그인 체크 필터 시작");
			//1. 사용자가 요청한 URL이 로그인 체크가 필요한지 확인
			if(isLongInCheckPath(requestURI)) { // 로그인이 필요한 경로인지 확인 (화이트리스트 인지 아닌지)
			//2. 로그인 체크가 필요하면 세션에서 로그인 정보를 받아오기
			HttpSession session = httpRequest.getSession(false); //false를 넣으면 없으면 null을 리턴시킴(세션객체를 안만든다)
			if(session == null || session.getAttribute("loginUser")==null) {
				//3-1. 로그인 정보가 업승면 로그인 페이지로 리다이렉트 시키기
				//로그인 정보가 없으면 
				log.info("인증되지 않은 사용자");
				//로그인 페이지로 리 다이렉트 
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				//ServletResponse 제공하는 리다이렉트 메소드
				httpResponse.sendRedirect("/users/login");
				//리턴을 하지 않으면 다음 필터로 계속 진행하기 때문에 return을 써줘야함
				return;
			}
			
			
			}
			//3-2. 로그인 정보가 있으면 다음 필터로 진행,
				// 로그인 체크가 필요하지 않거나 로그인 정보기 았으면 다음  필터로 이동
				chain.doFilter(request, response);
		}catch (Exception e) {
			throw e;
		}finally {
			log.info("로그인 체크 필터 종료");
		}
		
		
	
		
		
}
	
}

