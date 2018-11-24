package com.acroynon.bsse.validation;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.acroynon.bsse.model.dto.UserRegisterDTO;
import com.acroynon.bsse.service.UserService;

@Component
public class UserRegistrationValidator{

	@Autowired
	private UserService userService;
	private PasswordValidator passwordValidator;
	private UsernameValidator usernameValidator;
	
	public UserRegistrationValidator(){
		passwordValidator = new PasswordValidator();
		usernameValidator = new UsernameValidator();
	}
	
	
	public void validate(@Valid UserRegisterDTO dto, BindingResult result) {
		// Password Matches
		if(!dto.getPassword().equals(dto.getPasswordAgain())){
			result.rejectValue("password", "registration.password.mismatch");
		}
		// Secure Password
		if(!passwordValidator.isValid(dto.getPassword())){
			result.rejectValue("password", "registration.password.weak");
		}
		// Valid username
		if(!usernameValidator.isValid(dto.getUsername())){
			result.rejectValue("username", "registration.username.short");
		}
		// Username already exists
		if(userService.userExists(dto.getUsername())){
			result.rejectValue("username", "registration.username.alreadyExists");
		}		
	}


}
