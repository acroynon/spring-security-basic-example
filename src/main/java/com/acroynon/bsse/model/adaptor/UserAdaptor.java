package com.acroynon.bsse.model.adaptor;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.acroynon.bsse.model.data.Role;
import com.acroynon.bsse.model.data.User;
import com.acroynon.bsse.model.dto.UserDTO;
import com.acroynon.bsse.repository.RoleRepository;
import com.acroynon.bsse.repository.UserRepository;

@Component
public class UserAdaptor extends Adaptor<User, UserDTO>{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	public User adaptToSource(UserDTO dto){
		User user = userRepository.findUserByUsername(dto.getUsername());
		if(user == null){
			user = new User();
		}
		user.setUsername(dto.getUsername());
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setRoles(new ArrayList<Role>());
		user.setLocked(dto.isLocked());
		if(dto.isAdmin()){
			user.getRoles().add(roleRepository.findByRoleName("USER"));
		}else{
			user.getRoles().add(roleRepository.findByRoleName("ADMIN"));
		}
		return user;
	}
	
	public UserDTO adaptToDTO(User user){
		UserDTO dto = new UserDTO();
		dto.setUsername(user.getUsername());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setLocked(user.isLocked());
		boolean isAdmin = user.getRoles().stream()
				.filter(r -> r.getRoleName().equals("ADMIN")).findFirst().isPresent();
		dto.setAdmin(isAdmin);
		return dto;
	}
	
}
