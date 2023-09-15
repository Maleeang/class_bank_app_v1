package com.tencoding.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tencoding.bank.dto.SignUpFormDto;

@Controller
@RequestMapping("/user")
public class UserController {
	
	// 회원 가입 페이지 요청
	// http://localhost:80/user/sign-up
	@GetMapping("/sign-up")
	public String signUp() {
		return "user/signUp";
	}
	// 로그인 페이지 요청
	// http://localhost:80/user/sign-in
	@GetMapping("/sign-in")
	public String signIn() {
		return "user/signIn";
	}
	
	// 회원 가입 처리
	// http://localhost:80/user/sign-up
	// POST - HTTP body (데이터)
	// 데이터를 name속성을 이용해서 key - value 값으로 파싱
	@PostMapping("/sign-up")
	public String signUpProc(SignUpFormDto signUpFormDto) {
		System.out.println("signUpFormDto : " + signUpFormDto.toString());
		//1. 유효성 검사
		//2. 사용자 이미지 처리
		
		//3. 서비스 호출
		//4. 정상 처리 되었다면
		return "redirect:/user/sign-in";
	}
}
