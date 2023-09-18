package com.tencoding.bank.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tencoding.bank.dto.SignInFormDto;
import com.tencoding.bank.dto.SignUpFormDto;
import com.tencoding.bank.handler.exception.CustomRestfullException;
import com.tencoding.bank.repository.model.User;
import com.tencoding.bank.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private HttpSession session;
	
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
	
	//회원가입 처리
	//@param signUpFormDto
	//@return 리다이렉트 처리 - 로그인 페이지
	@PostMapping("/sign-up")
	public String signUpProc(SignUpFormDto signUpFormDto) {
		//0.인증 검사 - 여기서는 필요x
		//1.유효성 검사
		if(signUpFormDto.getUsername() == null
				|| signUpFormDto.getUsername().isEmpty()) {
			throw new CustomRestfullException("username을 입력하세요", HttpStatus.BAD_REQUEST);
		}
		if(signUpFormDto.getPassword() == null || signUpFormDto.getPassword().isEmpty()) {
			throw new CustomRestfullException("password을 입력하세요", HttpStatus.BAD_REQUEST);
		}
		if(signUpFormDto.getFullname() == null || signUpFormDto.getFullname().isEmpty()) {
			throw new CustomRestfullException("fullname을 입력하세요", HttpStatus.BAD_REQUEST);
		}
		
		//로직추가 --서비스 호출
		userService.signUp(signUpFormDto);
		
		return "redirect:/user/sign-in";
	}
	
	@PostMapping("/sign-in")
	public String signInProc(SignInFormDto signInFormDto) {
		//1.유효성 검사
		if(signInFormDto.getUsername() == null
				|| signInFormDto.getUsername().isEmpty()) {
			throw new CustomRestfullException("username을 입력하세요", HttpStatus.BAD_REQUEST);
		}
		if(signInFormDto.getPassword() == null || signInFormDto.getPassword().isEmpty()) {
			throw new CustomRestfullException("password을 입력하세요", HttpStatus.BAD_REQUEST);
		}
		
		//2.로직추가 --서비스 호출
		//principal <-- 접근 주체
		User principal = userService.signIn(signInFormDto);
		principal.setPassword(null);
		//3.쿠키 + 세션에 추가
		session.setAttribute("principal", principal);
		
		return "redirect:/account/list";
	}
	
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/user/sign-in";
	}
}
