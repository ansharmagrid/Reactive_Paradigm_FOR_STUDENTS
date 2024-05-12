package com.user.info.config;

import java.time.LocalDateTime;

import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;

import com.user.info.entities.LogRequest;

import reactor.core.publisher.Mono;

public class RequestResponseLoggingFilter implements ExchangeFilterFunction {

	private static final WebClient loggingWebClient = WebClient.create("http://localhost:8094/logging-service/log");

	@Override
	public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
		return next.exchange(request).doOnError(error -> {
			loggingWebClient.post().uri("")
					.bodyValue(LogRequest.builder().loggerName("USER-INFO-SERVICE").logLevel("ERROR")
							.logMessage(String.format("Error Message: %s%n Localized Message: %s%nError cause: %s%n",
									error.getMessage(), error.getLocalizedMessage(), error.getCause()))
							.logTimestamp(LocalDateTime.now().toString().replace("T", " ")).isLogSensitive(false))
					.exchangeToFlux(response -> response.bodyToFlux(String.class));
		}).doOnSuccess(response -> {
			response.bodyToMono(String.class).subscribe(responseBody -> {
				loggingWebClient.post().uri("")
						.bodyValue(LogRequest.builder().loggerName("USER-INFO-SERVICE").logLevel("ERROR")
								.logMessage(responseBody).logTimestamp(LocalDateTime.now().toString().replace("T", " "))
								.isLogSensitive(false))
						.exchangeToFlux(logResponse -> logResponse.bodyToFlux(String.class));
			});

		});
	}
}
