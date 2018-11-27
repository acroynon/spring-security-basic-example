package com.acroynon.bsse.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class PasswordValidator {

	public void validate(String password, BindingResult result){
		// Secure Password
		if(!isValid(password)){
			result.rejectValue("password", "password.tooWeak");
		}
	}
	
	public void validate(String password, String passwordAgain, BindingResult result){
		// Password Matches
		if(!password.equals(passwordAgain)){
			result.rejectValue("password", "password.mismatch");
		}
		validate(password, result);
				
	}
	
	// TODO: Secure password check
	private boolean isValid(String password){
		return true;
	}
	
}
