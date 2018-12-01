package com.acroynon.bsse.mvc;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.acroynon.bsse.model.adaptor.UserAdaptor;
import com.acroynon.bsse.model.data.User;
import com.acroynon.bsse.model.dto.PasswordDTO;
import com.acroynon.bsse.model.dto.UserDTO;
import com.acroynon.bsse.repository.UserRepository;	

@Controller
public class MvcController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserAdaptor userAdaptor;
	

	
	@GetMapping("/profile")
	public String profilePage(Model model, Principal principal){
		User user = getUserFromUsername(principal.getName());
		model.addAttribute("user", userAdaptor.adaptToDTO(user));
		return "profile";
	}
	
	@PostMapping("/updateUser")
	public String updateUser(Model model, Principal principal, @ModelAttribute UserDTO userDTO){
		User user = userAdaptor.adaptToSource(userDTO);
		userRepository.save(user);
		return profilePage(model, principal);
	} 
	
	@PostMapping("/updatePassword")
	public String updatePassword(Model model, Principal principal, @ModelAttribute PasswordDTO passwordDTO){
		User user = getUserFromUsername(principal.getName());
		user.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
		userRepository.save(user);
		return profilePage(model, principal);
	}
	
	@ExceptionHandler({UsernameNotFoundException.class})
	public String errorPage(Model model, Exception e){
		model.addAttribute("message", "Opps! Something went wrong!");
		return "error";
	}
	
	private User getUserFromUsername(String username){
		User user = userRepository.findUserByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("User does not exist: " + username);
		}
		return user;
	}
	
}
