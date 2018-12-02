package com.acroynon.ssbe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.acroynon.ssbe.service.UserService;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String getHomePage(Model model) {
		model.addAttribute("users", userService.getAllUsers());
		return "home";
	}
	
}
