package com.acroynon.ssbe.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.acroynon.ssbe.model.dto.PasswordDTO;
import com.acroynon.ssbe.model.dto.UserDTO;
import com.acroynon.ssbe.service.UserService;
import com.acroynon.ssbe.validation.UserValidator;

@RunWith(MockitoJUnitRunner.class)
public class ProfileControllerTest {

	@Mock
	private UserService userService;
	
	@Mock
	private UserValidator userValidator;
	
	@Mock
	private Principal principal;
	
	@Mock
	private Model model;
	
	@Mock
	private BindingResult bindingResult;
	
	@Mock
	private HttpSession session;
	
	@InjectMocks
	private ProfileController controller;
	
	@Test
	public void getProfilePage(){
		UserDTO dto = new UserDTO();
		String name = "name";
		Mockito.when(principal.getName()).thenReturn(name);
		Mockito.when(userService.getUserDTOFromUsername(Mockito.eq(name)))
			.thenReturn(dto);
		
		String result = controller.getProfilePage(principal, model);
		
		Assert.assertEquals("profile", result);
		Mockito.verify(principal).getName();
		andModelObjectsSet(name, dto);
	}
	
	@Test
	public void getProfileRedirect(){
		String result = controller.getProfileRedirect();
		Assert.assertEquals("redirect:/profile", result);
	}
	
	@Test
	public void updateUserDetails(){
		UserDTO dto = new UserDTO();
		String name = "name";
		Mockito.when(principal.getName()).thenReturn(name);
		Mockito.when(userService.getUserDTOFromUsername(Mockito.eq(name)))
			.thenReturn(dto);
		
		String result = controller.updateUserDetails(dto,  principal, model);
		
		Assert.assertEquals("profile", result);
		Mockito.verify(userService).updateUserDetails(dto);
		Mockito.verify(model).addAttribute(
				Mockito.eq("successMessage"), Mockito.eq("User details updated successfully"));
		andModelObjectsSet(name, dto);
	}
	
	@Test
	public void updateUserPassword_ValidPassword(){
		PasswordDTO passwordDTO = new PasswordDTO();
		UserDTO userDTO = new UserDTO();
		String name = "name";
		String password = "password";
		Mockito.when(bindingResult.hasErrors()).thenReturn(false);
		Mockito.when(principal.getName()).thenReturn(name);
		Mockito.when(userService.getUserDTOFromUsername(Mockito.eq(name)))
			.thenReturn(userDTO);
		passwordDTO.setPassword(password);
		
		
		String result = controller.updateUserPassword(passwordDTO, bindingResult, principal, model);
		
		Assert.assertEquals("profile", result);
		andUserValiated(name, passwordDTO);
		Mockito.verify(userService).updateUserPassword(Mockito.eq(name), Mockito.eq(password));
		Mockito.verify(model).addAttribute("successMessage", "Password updated successfully");
		andModelObjectsSet(name, userDTO, passwordDTO, model);
	}
	
	@Test
	public void updateUserPassword_InvalidPassword(){
		PasswordDTO passwordDTO = new PasswordDTO();
		UserDTO userDTO = new UserDTO();
		String name = "name";
		String password = "password";
		Mockito.when(bindingResult.hasErrors()).thenReturn(true);
		Mockito.when(principal.getName()).thenReturn(name);
		Mockito.when(userService.getUserDTOFromUsername(Mockito.eq(name)))
			.thenReturn(userDTO);
		
		
		String result = controller.updateUserPassword(passwordDTO, bindingResult, principal, model);
		
		Assert.assertEquals("profile", result);
		andUserValiated(name, passwordDTO);
		Mockito.verify(userService, Mockito.never()).updateUserPassword(Mockito.eq(name), Mockito.eq(password));
		Mockito.verify(model, Mockito.never()).addAttribute("SuccessMessage", "Password updated successfully");
		andModelObjectsSet(name, userDTO, passwordDTO, model);
	}
	
	@Test
	public void deleteUser(){
		String name = "name";
		Mockito.when(principal.getName()).thenReturn(name);
		
		String result = controller.deleteUser(principal, session);
		
		Assert.assertEquals("redirect:/", result);
		Mockito.verify(userService).deleteUserAccount(name);
		Mockito.verify(session).invalidate();
	}
	
	/** private helper methods **/
	private void andUserValiated(String name, PasswordDTO dto){
		Mockito.verify(userValidator).validate(name, dto, bindingResult);
	}
	
	private void andModelObjectsSet(String username, UserDTO dto){
		Mockito.verify(userService).getUserDTOFromUsername(Mockito.eq(username));
		Mockito.verify(model).addAttribute(Mockito.eq("user"), Mockito.eq(dto));
		Mockito.verify(model).addAttribute(Mockito.eq("password"), Mockito.any(PasswordDTO.class));
	}
	
	private void andModelObjectsSet(String username, UserDTO userDTO, PasswordDTO passwordDTO, Model model){
		Mockito.verify(userService).getUserDTOFromUsername(Mockito.eq(username));
		Mockito.verify(model).addAttribute(Mockito.eq("user"), Mockito.eq(userDTO));
		Mockito.verify(model).addAttribute(Mockito.eq("password"), Mockito.eq(passwordDTO));
	}

}
