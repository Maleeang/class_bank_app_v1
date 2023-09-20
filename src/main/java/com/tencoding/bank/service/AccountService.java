package com.tencoding.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tencoding.bank.dto.DepositFormDto;
import com.tencoding.bank.dto.SaveFormDto;
import com.tencoding.bank.dto.WithdrawFromDto;
import com.tencoding.bank.handler.exception.CustomRestfullException;
import com.tencoding.bank.repository.interfaces.AccountRepository;
import com.tencoding.bank.repository.interfaces.HistoryRepository;
import com.tencoding.bank.repository.model.Account;
import com.tencoding.bank.repository.model.History;

@Service //IOC 대상 + 싱글톤 패턴으로 -> 스프링 컨테이너 메모리에 객체가 생성
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private HistoryRepository historyRepository;
	
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

	//출금 기능 로직
	//1. 계좌 존재 여부 확인 -- select query
	//2. 본인 계좌 여부 확인
	//3. 계좌 비번 일치 확인
	//4. 잔액 여부 확인
	//5. 출금 처리 -- update query
	//6. 거래내역 등록 -- insert query
	//7. Transactional 처리
	@Transactional
	public void updateAccountWithdraw(WithdrawFromDto withdrawFromDto, Integer id) {
		//1
		Account accountEnitity = accountRepository.findByNumber(withdrawFromDto.getWAccountNumber());
		if(accountEnitity == null) {
			throw new CustomRestfullException("해당 계좌가 없습니다.", HttpStatus.BAD_REQUEST);
		}
		//2
		if(accountEnitity.getUserId() != id) {
			throw new CustomRestfullException("본인 계좌가 아닙니다.", HttpStatus.BAD_REQUEST);
		}
		//3
		if(accountEnitity.getPassword().equals(withdrawFromDto.wAccountPassword) == false) {
			throw new CustomRestfullException("계좌 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
		}
		//4
		if(accountEnitity.getBalance() < withdrawFromDto.amount) {
			throw new CustomRestfullException("계좌 잔액이 부족합니다.", HttpStatus.BAD_REQUEST);
		}
		//5 모델 객체 상태 변경 --> 객체를 업데이트 쿼리에 던지기
		accountEnitity.withdraw(withdrawFromDto.amount);
		accountRepository.updateById(accountEnitity);
		//6 History 객체 생성 --> insert
		History history = new History();
		history.setAmount(withdrawFromDto.amount);
		history.setWBalance(accountEnitity.getBalance());
		history.setDBalance(null);
		history.setWAccountId(accountEnitity.getId());
		history.setDAccountId(null);
		int resultRowCount = historyRepository.insert(history);
		if(resultRowCount != 1) {
			throw new CustomRestfullException("정상 처리 되지 않았습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	//입금 기능 로직
	//1. 계좌 존재 여부 확인
	//2. 입금 처리
	//3. 거래 내역 등록
	public void updateAccountDeposit(DepositFormDto depositFormDto) {
		//1
		Account accountEntity = accountRepository.findByNumber(depositFormDto.getDAccountNumber());
		if(accountEntity == null) {
			throw new CustomRestfullException("계좌번호가 없습니다.", HttpStatus.BAD_REQUEST);
		}
		//2
		accountEntity.deposit(depositFormDto.amount);
		accountRepository.updateById(accountEntity);
		//3
		History history = new History();
		history.setAmount(depositFormDto.amount);
		history.setDAccountId(accountEntity.getId());
		history.setWAccountId(null);
		history.setDBalance(accountEntity.getBalance());
		history.setWBalance(null);
		int resultRowCount = historyRepository.insert(history);
		if(resultRowCount !=1) {
			throw new CustomRestfullException("정상 처리 되지 않았습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
