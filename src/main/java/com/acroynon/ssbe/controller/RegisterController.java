package com.acroynon.ssbe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.acroynon.ssbe.model.dto.UserRegisterDTO;
import com.acroynon.ssbe.service.UserService;
import com.acroynon.ssbe.validation.UserValidator;

@Controller
public class RegisterController {

	@Autowired
	private UserValidator validator;
	@Autowired
	private UserService userService;
	
	@GetMapping("/register")
	public String registerPage(Model model){
		model.addAttribute("data", new UserRegisterDTO());
		return "register";
	}
	
	@PostMapping("/register")
	public String registerUser(@ModelAttribute("data") UserRegisterDTO dto, BindingResult result, Model model){
		validator.validate(dto, result);		
		if(result.hasErrors()){
			model.addAttribute("data", dto);
			return "register";
		}else{
			userService.addNewUser(dto);
			return "login";
		}
	}
	
}
