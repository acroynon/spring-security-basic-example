package com.acroynon.bsse.mvc;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;	

@Controller
public class MvcController {

	
	
	@ExceptionHandler({UsernameNotFoundException.class})
	public String errorPage(Model model, Exception e){
		model.addAttribute("message", "Opps! Something went wrong!");
		return "error";
	}

	
}
