package com.acroynon.ssbe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.acroynon.ssbe.model.dto.UserDTO;
import com.acroynon.ssbe.model.dto.UserUpdateDTO;
import com.acroynon.ssbe.service.UserService;

@Controller
@PropertySource("classpath:/messages.properties")
public class UserController {

	@Autowired
	private UserService userService;
	@Value("${admin.userCreated}")
	private String successMessage;
	
	@GetMapping("/user/{username}")
	public String updateUser(@PathVariable("username") String username, Model model){
		UserDTO user = userService.getUserDTOFromUsername(username);
		model.addAttribute("data",	adapt(user));
		model.addAttribute("user", user);
		return "user";
	}
	
	@PostMapping("/user/{username}")
	public String updateUser(@PathVariable("username") String username, @ModelAttribute UserUpdateDTO dto, Model model){
		// Delete (redirect to home)
		if(dto.getIsDeleted()){
			userService.deleteUserAccount(username);
			return "redirect:/";
		}
		// Lock
		if(dto.getIsLocked()){
			userService.lockUser(username);
		}else{
			userService.unlockUser(username);
		}
		// Admin
		if(dto.getIsAdmin()){
			userService.giveAdminRights(username);
		}else{
			userService.revokeAdminRights(username);
		}
		
		UserDTO user = userService.getUserDTOFromUsername(username);
		model.addAttribute("data", adapt(user));
		model.addAttribute("user", user);
		model.addAttribute("successMessage", successMessage);
		return "user";
	}
	
	private UserUpdateDTO adapt(UserDTO user){
		UserUpdateDTO dto = new UserUpdateDTO();
		dto.setIsAdmin(user.isAdmin());
		dto.setIsLocked(user.isLocked());
		return dto;
	}
	
}
