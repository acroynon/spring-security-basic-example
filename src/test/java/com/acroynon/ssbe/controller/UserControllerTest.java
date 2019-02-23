package com.acroynon.ssbe.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;

import com.acroynon.ssbe.model.dto.UserDTO;
import com.acroynon.ssbe.model.dto.UserUpdateDTO;
import com.acroynon.ssbe.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
	
	@Mock
	private UserService userService;
	
	@Mock
	private Model model;
	
	@InjectMocks
	private UserController controller;
	
	private String username;
	private UserDTO userDTO;
	private UserUpdateDTO userUpdateDTO;

	@Before
	public void setup(){
		username = "username";
		userDTO = new UserDTO();
		userUpdateDTO = new UserUpdateDTO();
		Mockito.when(userService.getUserDTOFromUsername(username)).thenReturn(userDTO);
	}
	
	@Test
	public void updateUser(){
		String result = controller.updateUser(username, model);
		
		Mockito.verify(model).addAttribute(Mockito.eq("data"), Mockito.any(UserUpdateDTO.class));
		Mockito.verify(model).addAttribute(Mockito.eq("user"), Mockito.eq(userDTO));
		Assert.assertEquals("user", result);
	}
	
	
	@Test
	public void updateUser_delete(){
		userUpdateDTO.setIsDeleted(true);
		
		String result = controller.updateUser(username, userUpdateDTO, model);
		
		Assert.assertEquals("redirect:/", result);
		thenUserDeleted();
		thenUserServiceNotCalledAgain();
		thenModelObjectsNotSet();
	}
	
	@Test
	public void updateUser_LockedRevokeAdmin(){
		userUpdateDTO.setIsDeleted(false);
		userUpdateDTO.setIsLocked(true);
		userUpdateDTO.setIsAdmin(false);
		
		String result = controller.updateUser(username, userUpdateDTO, model);
		
		Assert.assertEquals("user", result);
		thenUserLocked();
		thenUserRevokeAdminRights();
		thenUserNotDeleted();
		thenModelObjectsSet();
	}
	
	@Test
	public void updateUser_UnlockedGiveAdmin(){
		userUpdateDTO.setIsDeleted(false);
		userUpdateDTO.setIsLocked(false);
		userUpdateDTO.setIsAdmin(true);
		
		String result = controller.updateUser(username, userUpdateDTO, model);
		
		Assert.assertEquals("user", result);
		thenUserUnlocked();
		thenUserGivenAdminRights();
		thenUserNotDeleted();
		thenModelObjectsSet();
	}
	
	/** Private Helper Methods **/
	private void thenUserDeleted(){
		Mockito.verify(userService).deleteUserAccount(Mockito.eq(username));
	}
	
	private void thenUserNotDeleted(){
		Mockito.verify(userService, Mockito.never()).deleteUserAccount(Mockito.eq(username));
	}
	
	private void thenUserLocked(){
		Mockito.verify(userService).lockUser(username);
	}
	
	private void thenUserUnlocked(){
		Mockito.verify(userService).unlockUser(username);
	}
	
	private void thenUserGivenAdminRights(){
		Mockito.verify(userService).giveAdminRights(username);
	}
	
	private void thenUserRevokeAdminRights(){
		Mockito.verify(userService).revokeAdminRights(username);
	}
	
	private void thenUserServiceNotCalledAgain(){
		Mockito.verifyNoMoreInteractions(userService);
	}
	
	private void thenModelObjectsSet(){
		Mockito.verify(model).addAttribute(Mockito.eq("data"), Mockito.any(UserUpdateDTO.class));
		Mockito.verify(model).addAttribute(Mockito.eq("user"), Mockito.eq(userDTO));
		Mockito.verify(model).addAttribute(Mockito.eq("successMessage"), Mockito.any());
	}
	
	private void thenModelObjectsNotSet(){
		Mockito.verify(model, Mockito.never()).addAttribute(Mockito.eq("data"), Mockito.any(UserUpdateDTO.class));
		Mockito.verify(model, Mockito.never()).addAttribute(Mockito.eq("user"), Mockito.eq(userDTO));
		Mockito.verify(model, Mockito.never()).addAttribute(Mockito.eq("successMessage"), Mockito.anyString());
	}
	
}
