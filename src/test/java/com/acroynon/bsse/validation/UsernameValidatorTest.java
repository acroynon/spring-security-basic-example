package com.acroynon.bsse.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

import com.acroynon.ssbe.service.UserService;
import com.acroynon.ssbe.validation.UsernameValidator;

@RunWith(MockitoJUnitRunner.class)
public class UsernameValidatorTest {

	private static final String TOO_SHORT = "username.tooShort";
	private static final String ALREADY_EXISTS = "username.alreadyExists";
	private static final String VALID_USERNAME = "adam";
	private static final String INVALID_USERNAME = "a";
	
	@Mock
	private UserService userService;
	
	@Mock
	private BindingResult bindingResult;
	
	@InjectMocks
	private UsernameValidator validator;
	
	private String username;
	
	@Test
	public void whenUsernameValidAndDoesntExist(){
		whenUsernameValid();
		andUsernameDoesntExist();
		
		validateUsername();
		
		thenUsernameValid();
		thenUsernameDoesntExist();
	}
	
	@Test
	public void whenUsernameValidAndAlreadyExists() {
		whenUsernameValid();
		andUsernameAlreadyExists();
		
		validateUsername();
		
		thenUsernameValid();
		thenUsernameAlreadyExists();
	}
	
	@Test
	public void whenUsernameInvalidAndDoesntExist() {
		whenUsernameTooShort();
		andUsernameDoesntExist();
		
		validateUsername();
		
		thenUsernameTooShort();
		thenUsernameDoesntExist();
	}
	
	@Test
	public void whenUsernameInvalidAndAlreadyExists() {
		whenUsernameTooShort();
		andUsernameAlreadyExists();
		
		validateUsername();
		
		thenUsernameTooShort();
		thenUsernameAlreadyExists();
	}
	
	/** Private Helper Methods **/
	private void whenUsernameValid() {
		username = VALID_USERNAME;
	}
	
	private void whenUsernameTooShort() {
		username = INVALID_USERNAME;
	}
	
	private void andUsernameAlreadyExists() {
		Mockito.when(userService.userExists(Mockito.eq(username)))
			.thenReturn(true);
	}
	
	private void andUsernameDoesntExist() {
		Mockito.when(userService.userExists(Mockito.eq(username)))
		.thenReturn(false);
	}
	
	private void validateUsername() {
		validator.validate(username, bindingResult);
	}
	
	private void thenUsernameAlreadyExists() {
		Mockito.verify(bindingResult).rejectValue(
				Mockito.anyString(), Mockito.eq(ALREADY_EXISTS));
	}
	
	private void thenUsernameTooShort() {
		Mockito.verify(bindingResult).rejectValue(
				Mockito.anyString(), Mockito.eq(TOO_SHORT));
	}
	
	private void thenUsernameDoesntExist() {
		Mockito.verify(bindingResult, Mockito.never()).rejectValue(
				Mockito.anyString(), Mockito.eq(ALREADY_EXISTS));
	}
	
	private void thenUsernameValid() {
		Mockito.verify(bindingResult, Mockito.never()).rejectValue(
				Mockito.anyString(), Mockito.eq(TOO_SHORT));
	}

}
