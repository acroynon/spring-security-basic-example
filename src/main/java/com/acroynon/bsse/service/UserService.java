package com.acroynon.bsse.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.acroynon.bsse.model.adaptor.UserAdaptor;
import com.acroynon.bsse.model.data.Role;
import com.acroynon.bsse.model.data.User;
import com.acroynon.bsse.model.dto.UserCreateDTO;
import com.acroynon.bsse.model.dto.UserDTO;
import com.acroynon.bsse.model.dto.UserRegisterDTO;
import com.acroynon.bsse.repository.RoleRepository;
import com.acroynon.bsse.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
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
		user.setRoles(new ArrayList<Role>());
		user.getRoles().add(roleRepository.findByRoleName("USER"));
		userRepository.save(user);
	}
	
	public void addNewUser(UserCreateDTO dto){
		User user = new User();
		user.setUsername(dto.getUsername());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setRoles(new ArrayList<Role>());
		String roleName = "USER";
		if(dto.getIsAdmin()){
			roleName = "ADMIN";
		}
		user.getRoles().add(roleRepository.findByRoleName(roleName));
		userRepository.save(user);	
	}
	
}
