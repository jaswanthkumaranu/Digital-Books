package com.digitalbooks.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digitalbooks.model.UserVo;

@Repository
public interface UserRepository extends JpaRepository<UserVo,Long>{
	Optional<UserVo> findByUserName(String userName);
	Boolean existsByUserName(String userName);

	Boolean existsByEmailId(String emailId);

}
