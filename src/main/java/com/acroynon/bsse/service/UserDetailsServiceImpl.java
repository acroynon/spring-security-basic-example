package com.acroynon.bsse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acroynon.bsse.model.data.User;
import com.acroynon.bsse.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{	

	@Autowired
	private UserRepository userRepository;
	
	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByUsername(username);
		
		UserBuilder builder = null;
		if(user != null){
			builder = org.springframework.security.core.userdetails.User.withUsername(username);
			builder.password(user.getPassword());
			builder.roles(user.getRoles()
					.stream().map(r -> r.getRoleName())
					.toArray(String[]::new));
		}else{
			throw new UsernameNotFoundException(username);
		}		
		
		return builder.build();
	}

}
