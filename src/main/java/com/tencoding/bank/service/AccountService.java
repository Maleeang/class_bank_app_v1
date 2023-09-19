package com.tencoding.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tencoding.bank.dto.SaveFormDto;
import com.tencoding.bank.handler.exception.CustomRestfullException;
import com.tencoding.bank.repository.interfaces.AccountRepository;
import com.tencoding.bank.repository.model.Account;

@Service //IOC 대상 + 싱글톤 패턴으로 -> 스프링 컨테이너 메모리에 객체가 생성
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Transactional
	public void createAccount(SaveFormDto saveFormDto, Integer principalId) {
		if(accountRepository.findByNumber(saveFormDto.getNumber()) != null) {
			throw new CustomRestfullException("중복 계좌 번호", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Account account = new Account();
		account.setNumber(saveFormDto.getNumber());
		account.setPassword(saveFormDto.getPassword());
		account.setBalance(saveFormDto.getBalance());
		account.setUserId(principalId);
		
		int resultRowCount = accountRepository.insert(account);
		if(resultRowCount != 1) {
			throw new CustomRestfullException("계좌 생성 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Transactional
	public List<Account> selectAccount(Integer principalId) {
		List<Account> accountList;
		accountList = accountRepository.findByUserId(principalId);
		return accountList;
	}
	
}
