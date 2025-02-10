package com.example.post.controller;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class HomeController {
	@GetMapping("/")
	public String home() {
		log.info("home controller");
		// 예외 강제로 발생 해도 -> aftercopmpletion 인터셉트 작동 가능
//		throw new RuntimeException("강제로 발생 시킨 예외");
		return "index";
	}
}
