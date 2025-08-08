package com.vighnesh.mart.pojo;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
	public enum Role{
		ADMIN,
		CUSTOMER
	}
	public enum Status{
		ACTIVE,
		INACTIVE
	}
	private int user_id;
	private String username;
	private String email;
	private String password;
	private Role role;
	private Status status;
	private Timestamp created_at;
}
