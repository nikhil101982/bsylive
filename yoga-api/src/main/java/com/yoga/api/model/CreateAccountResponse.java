package com.yoga.api.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class CreateAccountResponse {

	String status;
	String message;
}
