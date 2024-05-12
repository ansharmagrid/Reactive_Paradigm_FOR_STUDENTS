package com.user.info.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Configuration
public class WebConfig {

	static int requestCount = 0;
	
	@Bean
	WebClient webClient() {
		return webClientBuilder().build();
	}
	
	@Bean
	WebClient.Builder webClientBuilder() {
		return WebClient.builder().filter(logRequest()).filter(logResponse());
	}

	private ExchangeFilterFunction logRequest() {
		return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
			System.out.println("Request: " + clientRequest.method() + " " + clientRequest.url());
			clientRequest.headers()
					.forEach((name, values) -> values.forEach(value -> System.out.println(name + "=" + value)));
			return Mono.just(clientRequest);
		});
	}

	private ExchangeFilterFunction logResponse() {
		return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
			System.out.println("Printing for request count: "+ (requestCount++));
			System.out.println("Response Status: " + clientResponse.statusCode());
			clientResponse.body((a,b) -> {
				System.out.println("Response body: "+a+"\t"+b.serverResponse());
				return null;
			});
			clientResponse.headers().asHttpHeaders()
					.forEach((name, values) -> values.forEach(value -> System.out.println(name + "=" + value)));
			return Mono.just(clientResponse);
		});
	}
}
