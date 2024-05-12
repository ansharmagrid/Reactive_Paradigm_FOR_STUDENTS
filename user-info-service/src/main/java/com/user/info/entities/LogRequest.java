package com.user.info.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogRequest {

	private String logId;
	private String loggerName;
	private String logMessage;
	private String logLevel;
	private String logTimestamp;
	private boolean isLogSensitive;
	
}
