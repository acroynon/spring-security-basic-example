package com.acroynon.ssbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acroynon.ssbe.model.data.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	
	User findUserByUsername(String username);

}
