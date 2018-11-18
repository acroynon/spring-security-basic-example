package com.acroynon.bsse.mvc;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.acroynon.bsse.model.adaptor.UserAdaptor;
import com.acroynon.bsse.model.data.User;
import com.acroynon.bsse.model.dto.PasswordDTO;
import com.acroynon.bsse.model.dto.UserDTO;
import com.acroynon.bsse.model.dto.UserRegisterDTO;
import com.acroynon.bsse.repository.UserRepository;	

@Controller
public class MvcController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserAdaptor userAdaptor;
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	@GetMapping("/")
	public String homePage(Model model) {
		List<User> users = userRepository.findAll();
		model.addAttribute("users", userAdaptor.adaptAllToDTO(users));
		return "home";
	}
	
	@GetMapping("/profile")
	public String profilePage(Model model, Principal principal){
		User user = getUserFromUsername(principal.getName());
		model.addAttribute("user", userAdaptor.adaptToDTO(user));
		return "profile";
	}
	
	@GetMapping("/user/{username}")
	public String userPage(Model model, @PathVariable("username") String username){
		User user = getUserFromUsername(username);
		model.addAttribute("user", userAdaptor.adaptToDTO(user));
		return "user";
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
	
	@GetMapping("/register")
	public String registerPage(){
		return "register";
	}
	
	@PostMapping("/register")
	private String registerUser(@ModelAttribute UserRegisterDTO userRegisterDTO){
		User user = new User();
		user.setUsername(userRegisterDTO.getUsername());
		user.setPassword(passwordEncoder.encode("pass1"));
		userRepository.save(user);
		return "login";
	}
	
	@DeleteMapping("/user/{username}")
	private String deleteUser(Model model, @PathVariable("username") String username){
		userRepository.delete(userRepository.findUserByUsername(username));
		return homePage(model);
	}
	
	@GetMapping("/createUser")
	private String createUserPage(){
		return "createUser";
	}
	
	@PostMapping("/createUser")
	private String createNewUser(@ModelAttribute UserDTO userDTO){
		User user = userAdaptor.adaptToSource(userDTO);
		userRepository.save(user);
		return "createUser";
	}
	
	private User getUserFromUsername(String username){
		User user = userRepository.findUserByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("User does not exist: " + username);
		}
		return user;
	}
	
}
