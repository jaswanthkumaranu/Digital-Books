package com.digitalbooks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalbooks.model.SubscribeBookVo;

public interface SubscribeBookRepository extends JpaRepository<SubscribeBookVo, Long>{

	List<SubscribeBookVo> findByReaderId(String readerId);
	
	List<SubscribeBookVo> findByEmailId(String emailId);
	

}
