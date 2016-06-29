package com.springapp.controller;

import com.springapp.model.Account;
import com.springapp.model.User;
import com.springapp.model.builder.AccountBuilder;
import com.springapp.model.builder.UserBuilder;
import com.springapp.mongo.model.MongoUser;
import com.springapp.mongo.repository.AccountRepository;
import com.springapp.mongo.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/")
public class MainController {

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private UserRepository userRepository;


	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		model.addAttribute("message", "Hello world!");
		return "hello";
	}

	@RequestMapping(value= "/test", method = RequestMethod.GET)
	public void printTest() {

		accountRepository.deleteAll();
		userRepository.deleteAll();

		// save a couple of accounts
		for (int i= 0; i<5; i++) {
			User user = new UserBuilder().withId(new ObjectId().toString()).withOpenId("openId").withUuid("uuid").withEmail("mathieu.neron@appdirect.com").withFirstName("Mathieu").withLastName("Neron").withLanguage("FR").build();
			Account account = new AccountBuilder().withStatus("ACTIVE").withUsers(Collections.singletonList(user)).build();
			accountRepository.save(account.toMongoAccount());
		}

		for (MongoUser mongoUser : userRepository.findAll()) {
			userRepository.delete(mongoUser);
		}

	}
}