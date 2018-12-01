package com.acroynon.bsse.model.dto;

import lombok.Data;

@Data
public class UserDTO {

	private String username;
	private String firstName;
	private String lastName;
	private boolean isAdmin;
	private boolean isLocked;
	
}
