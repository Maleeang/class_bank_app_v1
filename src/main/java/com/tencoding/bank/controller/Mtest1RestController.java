package com.tencoding.bank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tencoding.bank.handler.exception.CustomRestfullException;

@RestController
@RequestMapping("/macc")
public class Mtest1RestController {
	
	//http://localhost:80/macc/test
	@GetMapping("/test")
	public void test1() {
		throw new CustomRestfullException("잘못된연산", HttpStatus.BAD_REQUEST);
		//return "정상 동작";
	}
	
}
