package com.tencoding.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tencoding.bank.dto.SignUpFormDto;
import com.tencoding.bank.handler.exception.CustomRestfullException;
import com.tencoding.bank.repository.interfaces.UserRepository;

@Service //IoC 대상
public class UserService {
	
	//DAO - 데이터 베이스 연습
	@Autowired
	private UserRepository userRepository;
	
	//DI - 가지고오다
//	public UserService(UserRepository userRepository) {
//		this.userRepository = userRepository;
//	} = @Autowired
	
	@Transactional //정상 처리되면 commit(반영)함, 정상반영 안되면 Rollback 처리함
	public void signUp(SignUpFormDto signUpFormDto) {
		int result = userRepository.insert(signUpFormDto);
		if(result != 1) {
			throw new CustomRestfullException("회원가입 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
