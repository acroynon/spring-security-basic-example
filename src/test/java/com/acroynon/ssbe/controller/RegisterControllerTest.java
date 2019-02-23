package com.acroynon.ssbe.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.acroynon.ssbe.model.dto.UserRegisterDTO;
import com.acroynon.ssbe.service.UserService;
import com.acroynon.ssbe.validation.UserValidator;

@RunWith(MockitoJUnitRunner.class)
public class RegisterControllerTest {

	@Mock
	private UserValidator validator;
	
	@Mock
	private UserService userService;
	
	@Mock
	private Model model;
	
	@Mock
	private BindingResult bindingResult;
	
	@InjectMocks
	private RegisterController controller;
	
	private UserRegisterDTO dto;
	
	@Before
	public void setup(){
		dto = new UserRegisterDTO();
	}
	
	@Test
	public void getRegisterPage(){
		String result = controller.registerPage(model);
		Assert.assertEquals("register", result);
		Mockito.verify(model).addAttribute(
				Mockito.eq("data"), Mockito.any(UserRegisterDTO.class));	
	}
	
	@Test
	public void registerUser_invalid(){
		whenResultHasErrors();
		String result = controller.registerUser(dto, bindingResult, model);
		Mockito.verify(validator).validate(dto, bindingResult);
		Mockito.verify(model).addAttribute(Mockito.eq("data"), Mockito.eq(dto));
		Assert.assertEquals("register", result);
	}
	
	@Test
	public void registerUser_valid(){
		whenResultDoesntHaveErrors();
		String result = controller.registerUser(dto, bindingResult, model);
		Mockito.verify(validator).validate(dto, bindingResult);
		Mockito.verify(userService).addNewUser(Mockito.eq(dto));
		Assert.assertEquals("login", result);
	}
	
	/** Private Helper Methods **/
	private void whenResultHasErrors(){
		Mockito.when(bindingResult.hasErrors()).thenReturn(true);
	}
	
	private void whenResultDoesntHaveErrors(){
		Mockito.when(bindingResult.hasErrors()).thenReturn(false);
	}
	
}
