package com.springapp.controller;

import com.springapp.model.ErrorResult;
import com.springapp.model.Result;
import com.springapp.model.enums.ErrorCode;
import com.springapp.service.SubscriptionApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private SubscriptionApiService subscriptionApiService;

	@RequestMapping(value= "/create")
	public ResponseEntity<Result> createSubscription(@RequestParam("url") String url) {
		try {
			Result result = subscriptionApiService.createSubscription(url);
			return ok(result);
		} catch (Exception e) {
			logger.error("Exception thrown", e);
			return ok(new ErrorResult(ErrorCode.UNKNOWN_ERROR, e.getMessage()));
		}
	}

	@RequestMapping(value= "/change")
	public ResponseEntity<Result> changeSubscription(@RequestParam("url") String url) {
		try {
			Result result = subscriptionApiService.changeSubscription(url);
			return ok(result);
		} catch (Exception e) {
			logger.error("Exception thrown", e);
			return ok(new ErrorResult(ErrorCode.UNKNOWN_ERROR, e.getMessage()));
		}
	}

	@RequestMapping(value= "/cancel")
	public ResponseEntity<Result> cancelSubscription(@RequestParam("url") String url) {
		try {
			Result result = subscriptionApiService.cancelSubscription(url);
			return ok(result);
		} catch (Exception e) {
			logger.error("Exception thrown", e);
			return ok(new ErrorResult(ErrorCode.UNKNOWN_ERROR, e.getMessage()));
		}
	}

	@RequestMapping(value= "/notice")
	public ResponseEntity<Result> processSubscriptionNotice(@RequestParam("url") String url) {
		try {
			Result result = subscriptionApiService.processSubscriptionNotice(url);
			return ok(result);
		} catch (Exception e) {
			logger.error("Exception thrown", e);
			return ok(new ErrorResult(ErrorCode.UNKNOWN_ERROR, e.getMessage()));
		}
	}
}