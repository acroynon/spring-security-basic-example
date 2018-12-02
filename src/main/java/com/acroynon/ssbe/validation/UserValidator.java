package com.acroynon.ssbe.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.acroynon.ssbe.model.dto.PasswordDTO;
import com.acroynon.ssbe.model.dto.UserCreateDTO;
import com.acroynon.ssbe.model.dto.UserRegisterDTO;
import com.acroynon.ssbe.repository.UserRepository;

@Component
public class UserValidator{

	@Autowired
	private PasswordValidator passwordValidator;
	@Autowired
	private UsernameValidator usernameValidator;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private UserRepository userRepository;
	
	public void validate(UserRegisterDTO dto, BindingResult result) {
		usernameValidator.validate(dto.getUsername(), result);
		passwordValidator.validate(dto.getPassword(), dto.getPasswordAgain(), result);
	}
	
	public void validate(UserCreateDTO dto, BindingResult result){
		usernameValidator.validate(dto.getUsername(), result);
		passwordValidator.validate(dto.getPassword(), result);
	}
	
	public void validate(String username, PasswordDTO dto, BindingResult result){
		String userPassword = userRepository.findUserByUsername(username).getPassword();
		if(!encoder.matches(dto.getCurrentPassword(), userPassword)){
			result.rejectValue("currentPassword", "password.incorrect");
		}
		passwordValidator.validate(dto.getPassword(), dto.getPasswordAgain(), result);
	}


}
