package com.yoga.api.model;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class UserAccountResponse {

	List<CreateAccountRequest> userAccounts;

}
