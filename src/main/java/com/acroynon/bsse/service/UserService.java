package com.acroynon.bsse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.acroynon.bsse.model.adaptor.UserAdaptor;
import com.acroynon.bsse.model.data.User;
import com.acroynon.bsse.model.dto.UserDTO;
import com.acroynon.bsse.model.dto.UserRegisterDTO;
import com.acroynon.bsse.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserAdaptor userAdaptor;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<UserDTO> getAllUsers(){
		List<User> users = userRepository.findAll();
		return userAdaptor.adaptAllToDTO(users);
	}
	
	public boolean userExists(String username){
		return (userRepository.findUserByUsername(username) != null);
	}
	
	public void addNewUser(UserRegisterDTO dto){
		User user = new User();
		user.setUsername(dto.getUsername());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		userRepository.save(user);
	}
	
}
