package com.acroynon.ssbe.validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;

import com.acroynon.ssbe.model.data.User;
import com.acroynon.ssbe.model.dto.PasswordDTO;
import com.acroynon.ssbe.model.dto.UserCreateDTO;
import com.acroynon.ssbe.model.dto.UserRegisterDTO;
import com.acroynon.ssbe.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserValidatorTest {

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private PasswordEncoder encoder;
	
	@Mock
	private UsernameValidator usernameValidator;
	
	@Mock
	private PasswordValidator passwordValidator;
	
	@Mock
	private BindingResult bindingResult;
	
	@InjectMocks
	private UserValidator userValidator;
	
	private User user;
	private UserRegisterDTO userRegisterDTO;
	private UserCreateDTO userCreateDTO;
	private PasswordDTO passwordDTO;
	private String username;
	private String password;
	
	@Before
	public void setup(){
		userRegisterDTO = new UserRegisterDTO();
		userCreateDTO = new UserCreateDTO();
		passwordDTO = new PasswordDTO();
		user = new User();
		username = "username";
		password = "password";
		userRegisterDTO.setUsername(username);
		userRegisterDTO.setPassword(password);
		userRegisterDTO.setPasswordAgain(password);
		userCreateDTO.setUsername(username);
		userCreateDTO.setPassword(password);
		passwordDTO.setCurrentPassword(password);
		passwordDTO.setPassword(password);
		passwordDTO.setPasswordAgain(password);
		user.setPassword(password);
		Mockito.when(userRepository.findUserByUsername(username)).thenReturn(user);
	}
	
	@Test
	public void validateUserRegister(){
		userValidator.validate(userRegisterDTO, bindingResult);
		verifyUsernameValidation();
		verifyPasswordTwiceValidation();
	}
	
	@Test
	public void validateUserCreate(){
		userValidator.validate(userCreateDTO, bindingResult);
		verifyUsernameValidation();
		verifyPasswordValidation();
	}
	
	@Test
	public void validateLogin(){
		whenPasswordMatches();
		userValidator.validate(username, passwordDTO, bindingResult);		
		thenPasswordAccepted();
		verifyPasswordTwiceValidation();
	}
	
	@Test
	public void validateLogin_InvalidPassword(){
		whenPasswordDoesntMatch();
		userValidator.validate(username, passwordDTO, bindingResult);
		thenPasswordRejected();
		verifyPasswordTwiceValidation();
	}
	
	/** Private HelperMethods **/
	private void verifyUsernameValidation(){
		Mockito.verify(usernameValidator).validate(
				Mockito.eq(username), Mockito.eq(bindingResult));
	}
	
	private void verifyPasswordValidation(){
		Mockito.verify(passwordValidator).validate(
				Mockito.eq(password), Mockito.eq(bindingResult));
	}
	
	private void verifyPasswordTwiceValidation(){
		Mockito.verify(passwordValidator).validate(
				Mockito.eq(password), Mockito.eq(password), Mockito.eq(bindingResult));
	}
	
	private void whenPasswordMatches(){
		Mockito.when(encoder.matches(Mockito.anyString(), Mockito.anyString()))
			.thenReturn(true);
	}
	
	private void whenPasswordDoesntMatch(){
		Mockito.when(encoder.matches(Mockito.anyString(), Mockito.anyString()))
		.thenReturn(false);
	}
	
	private void thenPasswordRejected(){
		Mockito.verify(bindingResult).rejectValue(
				Mockito.anyString(), Mockito.anyString());
	}
	
	private void thenPasswordAccepted(){
		Mockito.verify(bindingResult, Mockito.never()).rejectValue(
				Mockito.anyString(), Mockito.anyString());
	}
	
}
