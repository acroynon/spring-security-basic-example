package com.acroynon.bsse.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.acroynon.bsse.model.dto.UserCreateDTO;
import com.acroynon.bsse.model.dto.UserRegisterDTO;

@Component
public class UserValidator{

	@Autowired
	private PasswordValidator passwordValidator;
	@Autowired
	private UsernameValidator usernameValidator;	
	
	public void validate(UserRegisterDTO dto, BindingResult result) {
		usernameValidator.validate(dto.getUsername(), result);
		passwordValidator.validate(dto.getPassword(), dto.getPasswordAgain(), result);
	}
	
	public void validate(UserCreateDTO dto, BindingResult result){
		usernameValidator.validate(dto.getUsername(), result);
		passwordValidator.validate(dto.getPassword(), result);
	}


}
