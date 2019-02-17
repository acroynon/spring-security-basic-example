package com.acroynon.ssbe.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

@RunWith(MockitoJUnitRunner.class)
public class ErrorControllerTest {

	@InjectMocks
	private ErrorController controller;
	
	@Test
	public void getPage() {
		String result = controller.handleError();
		Assert.assertEquals("error", result);
	}
	
	@Test
	public void exceptionPage(){
		String errorMessage = "error.msg";
		Exception e = Mockito.mock(Exception.class);
		Mockito.when(e.getMessage()).thenReturn(errorMessage);
		
		ModelAndView mav = controller.errorPage(e);
		
		Assert.assertEquals(errorMessage, mav.getModel().get("message"));
		Assert.assertEquals("error", mav.getViewName());
	}

}
