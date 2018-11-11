package com.acroynon.bsse.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;

@Entity
@Data
public class User {
	
	@Id
	@GeneratedValue
	private UUID guid;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	@ManyToMany
	@JoinTable(
			name = "user_roles",
			joinColumns = @JoinColumn(
					name = "user_guid", referencedColumnName = "guid"),
			inverseJoinColumns = @JoinColumn(
					name = "role_guid", referencedColumnName = "guid"))
	private List<Role> roles;
	
}
