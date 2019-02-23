package com.acroynon.ssbe.controller;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

	@InjectMocks
	private LoginController controller;
	
	@Test
	public void getPage() {
		String result = controller.loginPage();
		Assert.assertEquals("login", result);
	}

}
