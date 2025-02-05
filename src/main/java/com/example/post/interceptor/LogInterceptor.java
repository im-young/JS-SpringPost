package com.example.post.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

//인터셉터는 HandlerInterceptor 인터페이스를 구현한다.
// 필터는 자카르트에서 제공(서블릿과 연관된 동작을 함), 인터셉터는 스프링에서 제공(스프링과 좀더 친화적임)
@Slf4j
public class LogInterceptor implements HandlerInterceptor{
@Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
						// 필터에서는 servletrequest가 파라미터라서 형변환 시킨후 값을 받았는데, 여기서는 필요 없음	/ handler 타입이 object(뭐든 다 담을 수 있음-> if 사용)
		throws Exception {
		// 컨트롤러 보다 먼저 실행
	log.info("preHandle 실행");

		log.info("postHandle 실행");
		String requestURI = request.getRequestURI();

		if (handler instanceof HandlerMethod) {
				//호출할 컨트롤러의 메서드 정보를 담고 있다.
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			log.info("핸들러 메서드 :{}", handlerMethod);
		}
		log.info("라퀘스트 유알엘 :{}", requestURI);
	return true;
}
@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	// 컨트롤러의 핸들러 메소드(homecontroller)가 정상적으로 실행 된 후에 실행, 예외가 밸생하면 호출되지 않는다.
	//핸들러 메서드 : get 방식으로 root 에 대한 요청을 처리하는 메서드
	log.info("postHandle 실행");
	log.info("모델 and 뷰 :{}", modelAndView); // view : html 경로,파일명 (어떤 패이지를 보여줄꺼냐) , model : 이 html의 modelAttribute에 저장된 값

	
}
@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	//요청에 대한 응답이 완료 됐을 때 실행 , 예외 발생과 관계없이 호출( Exception ex 를 파라미터로 갖고 있어서)
									// 홈 컨트롤러에 예외발생 -> 리턴이 안됨(예외처리를 해야해서) -> postHandler는 작동 안함 -> 정상 실행되야 작동되서

			log.info("afterCompletion 실향");
			if(ex != null) {
				log.error("afterCompletion의 얘외 발생");
			}
			
			
	}
}
