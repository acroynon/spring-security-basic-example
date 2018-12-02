package com.acroynon.ssbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acroynon.ssbe.model.data.Role;

public interface RoleRepository extends JpaRepository<Role, String>{

	Role findByRoleName(String roleName);
	
}
