package com.user.info.controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.resource.NoResourceFoundException;

@Controller
@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler({DataAccessException.class})
	@ResponseBody
	public String handleDatabaseConnectionException() {
		return "connection to database broken";
	}
	
	@ExceptionHandler({WebClientRequestException.class})
	@ResponseBody
	public String handleExternalWebException() {
		return "Some of the services seems to be down";
	}
	
	@ExceptionHandler({NoResourceFoundException.class})
	@ResponseBody
	public String handleNoResourceFoundException(Exception ex) {
		return "You are requesting for something we don't have: "+ex.getLocalizedMessage();
	}
	
	@ExceptionHandler({Exception.class})
	@ResponseBody
	public String handleGlobalException(Exception ex) {
		ex.printStackTrace();
		return "Something went wrong which we didn't expect: "+ex.getClass();
	}
}
