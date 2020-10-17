package com.yoga.api.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class CreateAccountRequest {

	String userName;
	String password;
	String userEmail;
	boolean isLogin;
	String role;



}
