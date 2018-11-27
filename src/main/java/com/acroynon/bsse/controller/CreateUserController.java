package com.acroynon.bsse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.acroynon.bsse.model.dto.UserCreateDTO;
import com.acroynon.bsse.service.UserService;
import com.acroynon.bsse.validation.UserValidator;

@Controller
@PropertySource("classpath:/messages.properties")
public class CreateUserController {

	@Autowired
	private UserValidator validator;
	@Autowired
	private UserService userService;
	@Value("${admin.userCreated}")
	private String successMessage;
	
	@GetMapping("/createUser")
	private String createUserPage(Model model){
		model.addAttribute("data", new UserCreateDTO());
		return "createUser";
	}
	
	@PostMapping("/createUser")
	private String createNewUser(@ModelAttribute("data") UserCreateDTO dto, BindingResult result, Model model){
		validator.validate(dto, result);		
		if(!result.hasErrors()){
			userService.addNewUser(dto);
			model.addAttribute("successMessage", successMessage);
		}
		model.addAttribute("data", dto);
		return "createUser";
	}
	
}
