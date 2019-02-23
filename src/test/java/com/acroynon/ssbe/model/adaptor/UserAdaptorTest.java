package com.acroynon.ssbe.model.adaptor;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.acroynon.ssbe.model.data.Role;
import com.acroynon.ssbe.model.data.User;
import com.acroynon.ssbe.model.dto.UserDTO;
import com.acroynon.ssbe.repository.RoleRepository;
import com.acroynon.ssbe.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserAdaptorTest {

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private RoleRepository roleRepository;
	
	@InjectMocks
	private UserAdaptor adaptor;

	private UserDTO userDTO;
	private Role userRole;
	private Role adminRole;	
	
	@Before
	public void setup(){
		Role userRole = new Role();
		userRole.setRoleName("USER");
		Role adminRole = new Role();
		adminRole.setRoleName("ADMIN");
		userDTO = new UserDTO();
		userDTO.setFirstName("first.name");
		userDTO.setLastName("last.name");
		userDTO.setUsername("username");
	}
	
	@Test
	public void adaptToSource_admin() {
		userDTO.setAdmin(true);
		
		User user = adaptor.adaptToSource(userDTO);
		
		thenHasAdminRole(user.getRoles());
		thenAreEqual(userDTO, user);
	}
	
	@Test
	public void adaptToSource_user(){
		userDTO.setAdmin(false);
		
		User user = adaptor.adaptToSource(userDTO);
		
		thenHasUserRole(user.getRoles());
		thenAreEqual(userDTO, user);
	}
	
	@Test
	public void adaptToDTO_admin(){
		User user = getAdminUser();
		
		UserDTO dto = adaptor.adaptToDTO(user);
		
		Assert.assertTrue(dto.isAdmin());
		thenAreEqual(userDTO, user);
	}
	
	@Test
	public void adaptToDTO_user(){
		User user = getNormalUser();
		
		UserDTO dto = adaptor.adaptToDTO(user);
		
		Assert.assertFalse(dto.isAdmin());
		thenAreEqual(userDTO, user);
	}
	
	/** Private Helper Methods **/
	private void thenAreEqual(UserDTO userDTO, User user){
		Assert.assertEquals(userDTO.getUsername(), user.getUsername());
		Assert.assertEquals(userDTO.getFirstName(), user.getFirstName());
		Assert.assertEquals(userDTO.getLastName(), user.getLastName());
		Assert.assertEquals(userDTO.isLocked(), user.isLocked());
	}
	
	private void thenHasAdminRole(List<Role> roles){
		Assert.assertTrue(roles.contains(adminRole));
	}

	private void thenHasUserRole(List<Role> roles){
		Assert.assertTrue(roles.contains(userRole));
	}
	
	private User getNormalUser(){
		User user = getUserObject();
		return user;
	}
	
	private User getAdminUser(){
		User user = getUserObject();
		user.setRoles(getAdminRoles());
		return user;
	}
	
	private User getUserObject(){
		User user = new User();
		user.setUsername("username");
		user.setFirstName("first.name");
		user.setLastName("last.name");
		user.setRoles(new ArrayList<Role>());
		return user;
	}
	
	private List<Role> getAdminRoles(){
		List<Role> adminRoles = new ArrayList<Role>();
		Role adminRole = new Role();
		adminRole.setRoleName("ADMIN");
		adminRoles.add(adminRole);
		return adminRoles;
	}
	
}
