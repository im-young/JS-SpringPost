package com.example.post.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogFilter implements Filter {
	// 오버라이드 안하면 클래스명에 에러남(doFilter 메서드는 반드시 오버라이드 해야하는 메서드)

	// init, destroy 메서드는 해도되고 앤해도 됨
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("log 필터 생성");
	}

	@Override
	public void doFilter(ServletRequest request, // 요청이 들어왔을 때 생기는 객체(요청에 대한 정보를 담고 있는 객체)
			ServletResponse response, // 응답할 때 사용 되는 객체(응답에 대한 정보를 담고 있는 객체)
			FilterChain chain) //실행정보를 갖고 있음.  필터들이 여러개가 순차적으로 진행 될떄 (체인형식으로 (맞물려 있는 형식, 필터들의 결합)) 필터에 대한 체인 정보
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.info("dofilter 실향");// 새로고침 하면 이 로그 작성됨(이게 실행은 됨) -> 화면이 안나옴 (필터의 실행 순서에서 그 다음에 할일을 지정하지 않아서(리턴이
								// 없어서) 서블릿,컨트롤러까지 못감)-> 필터를 통과 시켜 줘야함

		// request 확인하기 -> request를 httpServeltrequest로 형변환
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		//클라이언트가 요청한 url경로 정보를 받아온다.
		String requestURI = httpRequest.getRequestURI(); // 실행 결과 stirng 타입으로 갖고오기 -> 내가 요청한 페이지를 알려줌(ex.
		// 사용자가 요청한 경로에 대한 정보갑을 확인 가능	-> 이걸로 로그인이 필요한 요청 경로인지 확인 할 수 있다. 	-> 경로에 대해 각가 다른 처리를 할 수 있음										
		log.info("requestURL{}:", requestURI);// requestURL/users/register:)
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		
		try {
			// 사용자가 요청한 경로에 대한 정보값을 확인 가능	-> 이걸로 로그인이 필요한 요청 경로인지 확인 할 수 있다. 	-> 경로에 대해 각가 다른 처리를 할 수 있음										
			log.info("requestURL{}:", requestURI);// requestURL/users/register:)
		// 다음 필터로 보내기 -> 밑에 코드가 작동하면 다음 서블릿과 컨트롤러로 보냄
		chain.doFilter(request, response);
	}catch (Exception e) {
		log.error("필터 실행 중 오률 바랭:{}",e.getMessage());
	}finally{ // 반드시 실행되는 블럭
		// 응답정보 로깅
		log.info("응답 ㅋ상태 코드:{}", httpResponse.getStatus());
		log.info("응답 컨텐츠 타입:{}", httpResponse.getContentType());
		log.info("응답 헤더- content- Lenguage:{}", httpResponse.getHeader("Content-Language"));
		log.info("응답 버퍼 크기:{}",httpResponse.getBufferSize());
	}
	}

	@Override
	public void destroy() {
		log.info("log 필터 종료");
	}

}
