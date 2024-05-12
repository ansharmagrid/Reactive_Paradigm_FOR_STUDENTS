package com.logging.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/logging-service")
@RequiredArgsConstructor
@ControllerAdvice
public class LoggingController {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	LogRequestRepository logRequestRepository;
	
	@PostMapping("/log")
	public String logMessage(@RequestBody LogRequest logRequest) {
		logRequestRepository.save(logRequest).subscribe(log -> {
			logger.info("Logged message with id: %s at level %s", log.getLogId(), log.getLogLevel());
		});
		return "success";
	}
}
