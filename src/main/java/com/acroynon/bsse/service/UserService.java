package com.acroynon.bsse.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
	
	public UserDTO getUserDTOFromUsername(String username){
		return userAdaptor.adaptToDTO(findUserByUsername(username));
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
	
	public void updateUserDetails(UserDTO dto){
		User user = findUserByUsername(dto.getUsername());
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		userRepository.save(user);
	}
	
	public void updateUserPassword(String username, String password){
		User user = findUserByUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);
	}
	
	public boolean passwordMatches(String username, String password){
		User user = findUserByUsername(username);
		return passwordEncoder.matches(password, user.getPassword());
	}
	
	public void giveAdminRights(String username){
		changeRole(username, "ADMIN");
	}
	
	public void revokeAdminRights(String username){
		changeRole(username, "USER");
	}
	
	public void lockUser(String username){
		changeLockStatus(username, true);
	}
	
	public void unlockUser(String username){
		changeLockStatus(username, false);
	}
	
	public void deleteUserAccount(String username){
		User user = findUserByUsername(username);
		userRepository.delete(user);
	}
	
	private void changeRole(String username, String roleName){
		List<Role> roleList = new ArrayList<Role>();
		roleList.add(roleRepository.findByRoleName(roleName));
		User user = findUserByUsername(username);
		user.setRoles(roleList);
		userRepository.save(user);
	}
	
	private void changeLockStatus(String username, boolean isLocked){
		User user = findUserByUsername(username);
		user.setLocked(isLocked);
		userRepository.save(user);
	}
	
	private User findUserByUsername(String username){
		User user = userRepository.findUserByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("Username does not exist: '" + username + "'");
		}
		return user;
	}
	
}
