package com.acroynon.bsse.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.acroynon.bsse.service.UserService;

@Component
public class UsernameValidator {

	private static int USERNAME_MIN_LENGTH = 3;
	@Autowired
	private UserService userService;
	
	public void validate(String username, BindingResult result){
		// Valid username
		if(username.length() < USERNAME_MIN_LENGTH){
			result.rejectValue("username", "username.tooShort");
		}
		// Username already exists
		if(userService.userExists(username)){
			result.rejectValue("username", "username.alreadyExists");
		}		
	}
	
}
