package com.acroynon.bsse.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.acroynon.bsse.model.dto.PasswordDTO;
import com.acroynon.bsse.model.dto.UserDTO;
import com.acroynon.bsse.service.UserService;
import com.acroynon.bsse.validation.UserValidator;

@Controller
@PropertySource("classpath:/messages.properties")
public class ProfileController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserValidator validator;

	@GetMapping("/profile")
	public String getProfilePage(Principal principal, Model model){
		setModelObjects(principal.getName(), model);
		return "profile";
	}
	
	@GetMapping("/profile/**")
	public String getProfileRedirect(){
		return "redirect:/profile";
	}
	
	@PostMapping("/profile")
	public String updateUserDetails(@ModelAttribute("user") UserDTO userDTO, Principal principal, Model model){
		userService.updateUserDetails(userDTO);		
		model.addAttribute("successMessage", "User details updated successfully");
		setModelObjects(principal.getName(), model);
		return "profile";
	}
	
	@PostMapping("/profile/password")
	public String updateUserPassword(@ModelAttribute("password") PasswordDTO passwordDTO, 
			BindingResult result, Principal principal, Model model){
		String username = principal.getName();
		validator.validate(username, passwordDTO, result);
		if(!result.hasErrors()){
			userService.updateUserPassword(username, passwordDTO.getPassword());
			model.addAttribute("successMessage", "Password updated successfully");
		}
		setModelObjects(username, passwordDTO, model);
		return "profile";
	}
	
	@PostMapping("/profile/delete")
	public String deleteUser(Principal principal, HttpSession session){
		userService.deleteUserAccount(principal.getName());
		session.invalidate();
		return "redirect:/";
	}
	
	private void setModelObjects(String username, PasswordDTO passwordDTO, Model model){
		UserDTO userDTO = userService.getUserDTOFromUsername(username);
		model.addAttribute("user", userDTO);
		model.addAttribute("password", passwordDTO);
	}
	
	private void setModelObjects(String username, Model model){
		setModelObjects(username, new PasswordDTO(), model);
	}
	
}
