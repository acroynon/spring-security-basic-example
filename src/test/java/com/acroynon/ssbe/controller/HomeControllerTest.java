package com.acroynon.ssbe.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;

import com.acroynon.ssbe.model.dto.UserDTO;
import com.acroynon.ssbe.service.UserService;

import org.junit.Assert;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

	@Mock
	private UserService userService;
	
	@Mock
	private Model model;
	
	@InjectMocks
	private HomeController controller;
	
	@Test
	public void getPage() {
		List<UserDTO> dtos = new ArrayList<>();
		Mockito.when(userService.getAllUsers()).thenReturn(dtos);
		
		String result = controller.getHomePage(model);
		
		Assert.assertEquals("home", result);
		Mockito.verify(userService).getAllUsers();
		Mockito.verify(model).addAttribute(Mockito.eq("users"), Mockito.eq(dtos));
	}

}
