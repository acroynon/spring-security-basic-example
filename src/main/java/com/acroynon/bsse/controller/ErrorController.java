package com.acroynon.bsse.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorController {
	
	@ExceptionHandler(Exception.class)
	public ModelAndView errorPage(Exception e){
		System.out.println("EXCEPTION HANDLER");
		ModelAndView mav = new ModelAndView();
		mav.addObject("message", e.getMessage());
		mav.setViewName("error");
		return mav;
	}
	
	@GetMapping("/error")
	public String handleError(){
		return "error";
	}
	
}
