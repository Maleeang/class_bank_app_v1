package com.tencoding.bank.repository.model;

import java.sql.Timestamp;

import org.springframework.http.HttpStatus;

import com.tencoding.bank.handler.exception.CustomRestfullException;

import lombok.Data;

@Data
public class Account {
	
	private Integer id;
	private String number;
	private String password;
	private Long balance;
	private Integer userId;
	private Timestamp createdAt;
	
	//출금 기능
	public void withdraw(Long amount) {
		this.balance -= amount;
	}
	//입금 기능
	public void deposit(Long amount) {
		this.balance += amount;
	}

	//계좌 소유자 확인
	public void checkOwner(Integer principalId) {
		if(userId != principalId) {
			throw new CustomRestfullException("본인계좌가 아닙니다.", HttpStatus.FORBIDDEN);
		}
	}
	
	//패스워드 체크
	public void checkPassword(String principalPassword) {
		if(password.equals(principalPassword) == false) {
			throw new CustomRestfullException("계좌 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
		}
	}
	
	//잔액확인
	public void checkBalance(Long amount) {
		if(balance < amount) {
			throw new CustomRestfullException("계좌 잔액이 부족합니다.", HttpStatus.BAD_REQUEST);
		}
	}



}
