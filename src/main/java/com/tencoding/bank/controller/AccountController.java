package com.tencoding.bank.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tencoding.bank.dto.DepositFormDto;
import com.tencoding.bank.dto.HistoryDto;
import com.tencoding.bank.dto.SaveFormDto;
import com.tencoding.bank.dto.TransferFormDto;
import com.tencoding.bank.dto.WithdrawFromDto;
import com.tencoding.bank.handler.exception.CustomRestfullException;
import com.tencoding.bank.handler.exception.UnAuthorizedException;
import com.tencoding.bank.repository.model.Account;
import com.tencoding.bank.repository.model.User;
import com.tencoding.bank.service.AccountService;
import com.tencoding.bank.utils.Define;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private HttpSession session;
	@Autowired
	private AccountService accountService;

	//계좌 목록 페이지
	//http/localhost:80/account/list
	@GetMapping({"/list", "/"})
	public String list(Model model) {
		//1.인증 여부 확인
		User user = (User)session.getAttribute(Define.PRINCIPAL);
		if(user == null) {
			throw new UnAuthorizedException("로그인 먼저 해요", HttpStatus.UNAUTHORIZED);
		}
		List<Account> resultAccountList = accountService.selectAccount(user.getId());
		model.addAttribute("resultAccountList", resultAccountList);
		return "account/list";
	}
	
	//계좌 생성 페이지
	//http://localhost/account/save
	@GetMapping("/save")
	public String save() {
		//1.인증 여부 확인
		User user = (User)session.getAttribute(Define.PRINCIPAL);
		if(user == null) {
			throw new UnAuthorizedException("로그인 먼저 해요", HttpStatus.UNAUTHORIZED);
		}
		return "account/save";
	}
	
	//계좌 생성 로직 구현
	@PostMapping("/save")
	public String saveProc(SaveFormDto saveFormDto) {
		//1.인증 여부 확인
		User user = (User)session.getAttribute(Define.PRINCIPAL);
		if(user == null) {
			throw new UnAuthorizedException("로그인 먼저 해요", HttpStatus.UNAUTHORIZED);
		}
		//2.유효성 검사
		if(saveFormDto.getNumber() == null || saveFormDto.getNumber().isEmpty()) {
			throw new CustomRestfullException("계좌번호를 입력해주세요.", HttpStatus.BAD_REQUEST);
		}
		if(saveFormDto.getPassword() == null || saveFormDto.getPassword().isEmpty()) {
			throw new CustomRestfullException("계좌 비밀번호를 입력해주세요.", HttpStatus.BAD_REQUEST);
		}
		if(saveFormDto.getBalance() == null || saveFormDto.getBalance() < 0) {
			throw new CustomRestfullException("잘못된 입력입니다.", HttpStatus.BAD_REQUEST);
		}
		//서비스 호출
		accountService.createAccount(saveFormDto, user.getId());
		
		
		return "redirect:/account/save";
	}
	
	//출금 페이지
	//http://localhost/account/withdraw
	@GetMapping("/withdraw")
	public String withdraw() {
		//1.인증 검사
		User user = (User)session.getAttribute(Define.PRINCIPAL);
		if(user == null) {
			throw new UnAuthorizedException("로그인 먼저 해요", HttpStatus.UNAUTHORIZED);
		}
		return "account/withdraw";
	}
	
	@PostMapping("/withdraw")
	public String withdrawProc(WithdrawFromDto withdrawFromDto) {
		
		//1.인증검사
		User user = (User)session.getAttribute(Define.PRINCIPAL);
		if(user == null) {
			throw new UnAuthorizedException("로그인 먼저 해요", HttpStatus.UNAUTHORIZED);
		}
		
		//2.유효성 검사
		if(withdrawFromDto.getAmount() == null) {
			throw new CustomRestfullException("금액을 입력하시오", HttpStatus.BAD_REQUEST);
		}
		if(withdrawFromDto.getAmount() <= 0) {
			throw new CustomRestfullException("0원 이하일 수 없습니다", HttpStatus.BAD_REQUEST);
		}
		if(withdrawFromDto.getWAccountNumber() == null || withdrawFromDto.getWAccountNumber().isEmpty()) {
			throw new CustomRestfullException("출금 계좌 번호를 입력 하시오", HttpStatus.BAD_REQUEST);
		}
		if(withdrawFromDto.getWAccountPassword() == null || withdrawFromDto.getWAccountPassword().isEmpty()) {
			throw new CustomRestfullException("출금 계좌 비밀번호를 입력 하시오", HttpStatus.BAD_REQUEST);
		}
		//3.서비스
		accountService.updateAccountWithdraw(withdrawFromDto, user.getId());
		return "redirect:/account/list";
		
	}
	
	//입금 페이지
	//http://localhost/account/deposit
	@GetMapping("/deposit")
	public String deposit() {
		//1.인증검사
		User user = (User)session.getAttribute(Define.PRINCIPAL);
		if(user == null) {
			throw new UnAuthorizedException("로그인 먼저 해요", HttpStatus.UNAUTHORIZED);
		}
		return "account/deposit";
	}
	
	@PostMapping("/deposit")
	public String depositProc(DepositFormDto depositFormDto) {
		//1.인증검사
		User user = (User)session.getAttribute(Define.PRINCIPAL);
		if(user == null) {
			throw new UnAuthorizedException("로그인 먼저 해요", HttpStatus.UNAUTHORIZED);
		}
		
		//2.유효성 검사
		if(depositFormDto.getAmount() == null) {
			throw new CustomRestfullException("금액을 입력하시오", HttpStatus.BAD_REQUEST);
		}
		if(depositFormDto.getAmount() <= 0) {
			throw new CustomRestfullException("0원 이하일 수 없습니다", HttpStatus.BAD_REQUEST);
		}
		if(depositFormDto.getDAccountNumber() == null || depositFormDto.getDAccountNumber().isEmpty()) {
			throw new CustomRestfullException("입금 계좌 번호를 입력 하시오", HttpStatus.BAD_REQUEST);
		}
		//3
		accountService.updateAccountDeposit(depositFormDto);
		return "redirect:/account/list";
	}
	
	//이체 페이지
	//http://localhost/account/transfer
	@GetMapping("/transfer")
	public String transfer() {
		//1.인증검사
		User user = (User)session.getAttribute(Define.PRINCIPAL);
		if(user == null) {
			throw new UnAuthorizedException("로그인 먼저 해요", HttpStatus.UNAUTHORIZED);
		}
		return "account/transfer";
	}
	
	//1. 출금 계좌 번호 확인
	//2. 입금 계좌 번호 확인
	//3. 출금 계좌 비밀번호 입력 여부 확인
	//4. 이체 금액 확인
	@PostMapping("/transfer")
	public String transferProc(TransferFormDto transferFormDto) {
		//1.인증검사
		User user = (User)session.getAttribute(Define.PRINCIPAL);
		if(user == null) {
			throw new UnAuthorizedException("로그인 먼저 해요", HttpStatus.UNAUTHORIZED);
		}
		//2.유효성 검사
		//1) 출금 계좌 번호 확인
		if(transferFormDto.wAccountNumber == null || transferFormDto.wAccountNumber.isEmpty()) {
			throw new CustomRestfullException("출금 계좌 번호를 입력하시오", HttpStatus.BAD_REQUEST);
		}
		//2) 입금 계좌 번호 확인
		if(transferFormDto.dAccountNumber == null || transferFormDto.dAccountNumber.isEmpty()) {
			throw new CustomRestfullException("입금 계좌 번호를 입력하시오", HttpStatus.BAD_REQUEST);
		}
		//3) 출금 계좌 비밀번호 입력 여부 확인
		if(transferFormDto.wAccountPassword == null || transferFormDto.wAccountPassword.isEmpty()) {
			throw new CustomRestfullException("출금 계좌 비밀번호를 입력하시오", HttpStatus.BAD_REQUEST);
		}		
		//4) 이체 금액 확인
		if(transferFormDto.amount <= 0) {
			throw new CustomRestfullException("이체 금액은 0원 이하일 수 없습니다.", HttpStatus.BAD_REQUEST);
		}
		if(transferFormDto.getAmount() == null) {
			throw new CustomRestfullException("이체 금액은 0원 이하일 수 없습니다.", HttpStatus.BAD_REQUEST);
		}
		//3.서비스 호출
		accountService.updateAccountTransfer(transferFormDto, user.getId());
		
		return "redirect:/account/list";
	}
	
	//TODO - 수정하기
	//상세 보기 페이지
	//http://localhost/account/detail/1?type=all,deposit,withdraw
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable Integer id, @RequestParam(name = "type", defaultValue = "all", required = false) String type, Model model) {
		//1.인증검사
		User user = (User)session.getAttribute(Define.PRINCIPAL);
		if(user == null) {
			throw new UnAuthorizedException("로그인 먼저 해요", HttpStatus.UNAUTHORIZED);
		}
		//2.서비스 호출
		//Account , List History
		Account account = accountService.readAccount(id);
		
		List<HistoryDto> historyList = accountService.readHistoryListByAccount(id, type); 
		model.addAttribute("account", account);
		model.addAttribute("historyList", historyList);
		return "account/detail";
	}
	

}
