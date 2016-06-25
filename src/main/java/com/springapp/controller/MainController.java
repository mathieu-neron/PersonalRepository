package com.springapp.controller;

import com.springapp.mongo.repository.AccountRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class MainController {

//	@Autowired
//	private AccountRepository accountRepository;


	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		model.addAttribute("message", "Hello world!");
		return "hello";
	}

	@RequestMapping(value= "/test", method = RequestMethod.GET)
	public void printTest(ModelMap model) {
//		accountRepository.deleteAll();
//
//		// save a couple of customers
//		accountRepository.save(new MongoAccount("testsss"));
//		accountRepository.save(new MongoAccount("wwwwaa"));
//		accountRepository.save(new MongoAccount("wwwsss"));
//		accountRepository.save(new MongoAccount("eeddww"));
//
//		// fetch all customers
//		System.out.println("Customers found with findAll():");
//		System.out.println("-------------------------------");
//		for (MongoAccount account : accountRepository.findAll()) {
//			System.out.println(account);
//		}
//		System.out.println();
//
//		// fetch an individual customer
//		System.out.println("Customer found with findByFirstName('Alice'):");
//		System.out.println("--------------------------------");
//		System.out.println(accountRepository.findByAccountIdentifier("Alice"));
	}
}