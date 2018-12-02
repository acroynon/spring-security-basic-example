package com.acroynon.ssbe.model.data;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;

@Entity
@Data
public class Role {
	
	@Id
	@GeneratedValue
	private UUID guid;
	
	private String roleName;
	
	@ManyToMany(mappedBy = "roles")
	private List<User> user;
	
}
