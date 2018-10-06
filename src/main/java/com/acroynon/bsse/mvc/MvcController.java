package com.acroynon.bsse.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcController {

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	@GetMapping("/")
	public String homePage() {
		return "home";
	}
	
	@GetMapping("/admin")
	public String adminPage() {
		return "admin";
	}
	
}
