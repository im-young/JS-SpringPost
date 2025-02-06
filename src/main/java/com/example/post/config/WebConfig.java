package com.example.post.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.post.filter.LogFilter;
import com.example.post.filter.LoginCheckFilter;
import com.example.post.interceptor.LogInterceptor;
import com.example.post.interceptor.LoginCheckIntercptor;

import jakarta.servlet.Filter;

@Configuration //스프링 설정과 관련된 클래스 : 컨포넌티가 아니라서 spring 빈으로 등록안됨(메서드에서 빈을 등록해서 사용) 
//@Configuration 클래스의 @Bean 메서드의 리턴 값이 스피링 빈으로 등록된다.
public class WebConfig implements WebMvcConfigurer {

//	@Bean // 이걸 주석처리하면 빈으로 등록되지 않아서 필터자체가 객체로 만들어 지지 않음
	public FilterRegistrationBean logFilter() {
		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
		// 사용할 필터를 지정한다.
				filterRegistrationBean.setFilter(new LogFilter());
		//필터의 순서를 지정한다.
			// 필터가 여러개 있을 경우, setOrder()의 숫자가 낮을수록 먼저 동작.(1이 제일작음)
		filterRegistrationBean.setOrder(1);
		//필터를 적용할 URL 패턴을 지정한다.
		filterRegistrationBean.addUrlPatterns("/*"); //"/*" : 모든 경로 지정 (add 설명에 파리미터 부분 : String... urlPatterns 라고 적혀 있으면 ',' 로 파라미터 나열 가능)
												// root 밑으로 요청되는 모든 경로에 대해 로그필터를 적용하겠다. -> 어느곳에서 요청을 보내도(로그인 페이지를 가도 ,회원가입 페이지를 가도) dofilter 실행됨
		return filterRegistrationBean;
	}
//	@Bean
	public FilterRegistrationBean loginCheckFilter() {
		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
		// 사용할 필터를 지정한다.
				filterRegistrationBean.setFilter(new LoginCheckFilter());
		//필터의 순서를 지정한다.
			// 필터가 여러개 있을 경우, setOrder()의 숫자가 낮을수록 먼저 동작.(1이 제일작음)
		filterRegistrationBean.setOrder(1);
		//필터를 적용할 URL 패턴을 지정한다.
		filterRegistrationBean.addUrlPatterns("/*"); //"/*" : 모든 경로 지정 (add 설명에 파리미터 부분 : String... urlPatterns 라고 적혀 있으면 ',' 로 파라미터 나열 가능)
												// root 밑으로 요청되는 모든 경로에 대해 로그필터를 적용하겠다. -> 어느곳에서 요청을 보내도(로그인 페이지를 가도 ,회원가입 페이지를 가도) dofilter 실행됨
		return filterRegistrationBean;
	}
	
	//====  Day  ---
	//===Day0206 : 예외처리 위해 주석처리 : 이거 실행되면 계속 로그인 하라고 뜸
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
////		// 여기서 인터셉트를 등록함
////		registry.addInterceptor(new LogInterceptor()) // 파라미터로 handle
////		// 인터셉터의 실행 순서
////		.order(1)
////		//인터셉터를 적용할 url 패턴을 지정 
////		.addPathPatterns("/**") ;// "/**" 모든 경로(필터에서는"/*")
//		
//		//--- LoginCheckIntercptor 
//		registry.addInterceptor(new LoginCheckIntercptor())
//		.order(2)
//		.addPathPatterns("/**")
//		.excludePathPatterns("/","users/register","/users/login","users/logout"); // 제외할 html 경로들(필터에서 whitelist)
//	}
	
	
}
