package com.springapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/subscription")
public class SubscriptionController {

	@RequestMapping(value= "/create")
	public void createSubscription() {
	}

	@RequestMapping(value= "/change")
	public void changeSubscription() {
	}

	@RequestMapping(value= "/cancel")
	public void cancelSubscription() {
	}

	@RequestMapping(value= "/status")
	public void getSubscriptionStatus() {
	}
}