package com.yoga.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yoga.api.entity.UserAccountEntity;
import com.yoga.api.model.CreateAccountReq;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.model.UserAccountId;
import com.yoga.api.model.UserAccountResponse;
import com.yoga.api.repository.UserAccountRepository;
import com.yoga.api.service.AdminUserAccountService;
import com.yoga.api.service.UserAccountService;

@RestController
@CrossOrigin
public class UserAccountController {

	@Autowired
	UserAccountRepository userAccountRepository;

	@Autowired
	UserAccountService userAccountService;
	
	@Autowired
	AdminUserAccountService adminUserAccountService;
	

	@PostMapping("/createAccount")
	public StatusMessageResponse createUserAccount(final @RequestBody CreateAccountReq createAccountRequest)
			throws Exception {
		return userAccountService.createUserAccount(createAccountRequest);
	}

	@GetMapping("/userAccounts")
	public UserAccountResponse userAccounts() throws Exception {
		return userAccountService.userAccounts();
	}

	@GetMapping(value = "/allUserAccountEntityDetails")
	public List<UserAccountEntity> getUserAccountEntityDetails() throws Exception {
		return userAccountRepository.findAll();

	}
	
	@PostMapping(value = "/removeUser/{userEmail}")
	public StatusMessageResponse removeUser(@PathVariable("userEmail") String userEmail) throws Exception {
		return userAccountService.removeUser(userEmail);

	}

	@GetMapping(value = "/userEmails")
	UserAccountId getUserEmailId() {
		return userAccountService.getUserEmailId();

	}
	
	@PostMapping("/createAccountByAdmin")
	public StatusMessageResponse createAccountByAdmin(final @RequestBody CreateAccountReq createAccountRequest)
			throws Exception {
		return adminUserAccountService.createAccountByAdmin(createAccountRequest);
	}

}
