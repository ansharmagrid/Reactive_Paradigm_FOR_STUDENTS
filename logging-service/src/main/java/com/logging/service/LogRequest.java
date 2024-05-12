package com.logging.service;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Document
@AllArgsConstructor
public class LogRequest {

	@Id
	private String logId;
	private String loggerName;
	private String logMessage;
	private String logLevel;
	private String logTimestamp;
	private String isLogSensitive;
}
