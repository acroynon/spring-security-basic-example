package com.acroynon.ssbe.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.acroynon.ssbe.model.dto.UserCreateDTO;
import com.acroynon.ssbe.service.UserService;
import com.acroynon.ssbe.validation.UserValidator;

@RunWith(MockitoJUnitRunner.class)
public class CreateUserControllerTest {

	@Mock
	private UserValidator validator;
	
	@Mock
	private UserService userService;
	
	@Mock
	private Model model;
	
	@Mock
	private BindingResult bindingResult;
	
	@InjectMocks
	private CreateUserController controller;
	
	@Test
	public void getPage() {
		String result = controller.createUserPage(model);
		
		Mockito.verify(model).addAttribute(
				Mockito.eq("data"), Mockito.any(UserCreateDTO.class));
		Assert.assertEquals("createUser", result);
	}

	@Test
	public void postValidDTO(){
		UserCreateDTO dto = new UserCreateDTO();
		Mockito.when(bindingResult.hasErrors()).thenReturn(false);
		
		String result = controller.createNewUser(dto, bindingResult, model);
		
		Assert.assertEquals("createUser", result);
		Mockito.verify(userService).addNewUser(dto);
		Mockito.verify(validator).validate(dto, bindingResult);
		Mockito.verify(model).addAttribute(Mockito.eq("data"), Mockito.eq(dto));
		Mockito.verify(model).addAttribute(Mockito.eq("successMessage"), Mockito.any());
	}
	
	@Test
	public void postInvalidDTO(){
		UserCreateDTO dto = new UserCreateDTO();
		Mockito.when(bindingResult.hasErrors()).thenReturn(true);
		
		String result = controller.createNewUser(dto, bindingResult, model);
		
		Assert.assertEquals("createUser", result);
		Mockito.verify(userService, Mockito.never()).addNewUser(dto);
		Mockito.verify(validator).validate(dto, bindingResult);
		Mockito.verify(model).addAttribute(Mockito.eq("data"), Mockito.eq(dto));
		Mockito.verify(model, Mockito.never()).addAttribute(Mockito.eq("successMessage"), Mockito.anyString());
	}
}
