package com.acroynon.ssbe;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.acroynon.ssbe.model.data.Role;
import com.acroynon.ssbe.model.data.User;
import com.acroynon.ssbe.repository.RoleRepository;
import com.acroynon.ssbe.repository.UserRepository;

@SpringBootApplication
public class Application {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void setupUsers(){
		User user = new User();
		user.setUsername("user");
		user.setPassword(passwordEncoder.encode("pass1"));
		List<Role> userRoles = new ArrayList<Role>();
		Role userRole = new Role();
		userRole.setRoleName("USER");
		userRoles.add(userRole);
		user.setRoles(userRoles);
		roleRepository.saveAndFlush(userRole);
		userRepository.saveAndFlush(user);
		
		User admin = new User();
		admin.setUsername("admin");
		admin.setPassword(passwordEncoder.encode("pass1"));
		List<Role> adminRoles = new ArrayList<Role>();
		Role adminRole = new Role();
		adminRole.setRoleName("ADMIN");
		adminRoles.add(adminRole);
		admin.setRoles(adminRoles);
		roleRepository.saveAndFlush(adminRole);
		userRepository.saveAndFlush(admin);
	}
}
