package com.acroynon.ssbe.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

@RunWith(MockitoJUnitRunner.class)
public class PasswordValidatorTest {
	
	private static final String VALID_PASSWORD = "Password1!";
	private static final String INVALID_PASSWORD = "abc";
	private static final String TOO_WEAK = "password.tooWeak";
	private static final String MISMATCH = "password.mismatch";
	
	
	@InjectMocks
	private PasswordValidator validator;
	
	@Mock
	private BindingResult bindingResult;
	
	@Test
	public void validPassword() {
		whenGivenValidPassword();
		thenZeroErrors();
	}
	
	@Test
	public void invalidPassword() {
		whenGivenInvalidPassword();
		thenPasswordTooWeak();
	}
	
	@Test
	public void matchingValidPasswords() {
		whenGivenValidMatchingPasswords();
		andPasswordNotMismatch();
		andPasswordNotTooWeak();
	}
	
	@Test
	public void matchingInvalidPasswords() {
		whenGivenInvalidMatchingPasswords();
		thenPasswordTooWeak();
		andPasswordNotMismatch();
	}
	
	@Test
	public void notMatchingAndInvalidPasswords() {
		whenGivenNotMatchingPasswords();
		thenPasswordMismatch();
		thenPasswordTooWeak();
	}
	
	/** Private Helper Methods **/
	private void whenGivenValidPassword() {
		validator.validate(VALID_PASSWORD, bindingResult);
	}
	
	private void whenGivenInvalidPassword() {
		validator.validate(INVALID_PASSWORD, bindingResult);
	}
	
	private void whenGivenValidMatchingPasswords() {
		validator.validate(VALID_PASSWORD, VALID_PASSWORD, bindingResult);
	}
	
	private void whenGivenInvalidMatchingPasswords() {
		validator.validate(INVALID_PASSWORD, INVALID_PASSWORD, bindingResult);
	}
	
	private void whenGivenNotMatchingPasswords() {
		validator.validate(INVALID_PASSWORD, VALID_PASSWORD, bindingResult);
	}
	
	private void thenZeroErrors() {
		Mockito.verify(bindingResult, Mockito.never()).rejectValue(
				Mockito.anyString(), Mockito.anyString());
	}
	
	private void thenPasswordTooWeak() {
		Mockito.verify(bindingResult).rejectValue(
				Mockito.anyString(), Mockito.eq(TOO_WEAK));
	}
	
	private void thenPasswordMismatch() {
		Mockito.verify(bindingResult).rejectValue(
				Mockito.anyString(), Mockito.eq(MISMATCH));
	}

	
	private void andPasswordNotMismatch() {
		Mockito.verify(bindingResult, Mockito.never()).rejectValue(
				Mockito.anyString(), Mockito.eq(MISMATCH));
	}
	
	private void andPasswordNotTooWeak() {
		Mockito.verify(bindingResult, Mockito.never()).rejectValue(
				Mockito.anyString(), Mockito.eq(TOO_WEAK));
	}

}
