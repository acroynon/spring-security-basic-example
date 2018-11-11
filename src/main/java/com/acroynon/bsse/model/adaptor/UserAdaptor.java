package com.acroynon.bsse.model.adaptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.acroynon.bsse.model.data.User;
import com.acroynon.bsse.model.dto.UserDTO;
import com.acroynon.bsse.repository.UserRepository;

@Component
public class UserAdaptor extends Adaptor<User, UserDTO>{

	@Autowired
	private UserRepository userRepository;
	
	public User adaptToSource(UserDTO dto){
		return userRepository.findUserByUsername(dto.getUsername());
	}
	
	public UserDTO adaptToDTO(User user){
		UserDTO dto = new UserDTO();
		dto.setUsername(user.getUsername());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		return dto;
	}
	
}
