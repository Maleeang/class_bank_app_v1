package com.tencoding.bank.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tencoding.bank.repository.model.History;

@Mapper
public interface HistoryRepository {
	
	public int insert(History history);
	public int updateById(History history);
	public int deleteById(Integer id);
	public List<History> findAll(); 
	public History findById(Integer id);

}
