package com.acroynon.bsse.model.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {

	private Boolean isAdmin;
	private Boolean isLocked;
	private Boolean isDeleted;
	
}
