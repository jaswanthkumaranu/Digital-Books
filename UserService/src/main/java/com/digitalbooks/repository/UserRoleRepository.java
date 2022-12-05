package com.digitalbooks.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalbooks.model.ERole;
import com.digitalbooks.model.UserRoleVo;

public interface UserRoleRepository extends JpaRepository<UserRoleVo,Long> {
	Optional<UserRoleVo> findByName(ERole name);
}
