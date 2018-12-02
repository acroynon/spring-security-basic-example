package com.acroynon.ssbe.model.dto;

import lombok.Data;

@Data
public class UserCreateDTO {

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private Boolean isAdmin;
	
}
