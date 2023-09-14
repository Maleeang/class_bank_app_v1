package com.tencoding.bank.repository.interfaces;

import java.util.List;
//ibatis -> 2.4버전 이후로 MyBatis로 이름이 변경됨
import org.apache.ibatis.annotations.Mapper;

import com.tencoding.bank.repository.model.User;

@Mapper //반드시 기술 해주어야 동작 한다.
public interface UserRepository {
	
	public int insert(User user);
	public int updateById(User user);
	public int deleteById(Integer id);
	public User findById(Integer id);
	//회원정보 리스트
	public List<User> findAll();
	
}
