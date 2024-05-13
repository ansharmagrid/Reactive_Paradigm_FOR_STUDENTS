package com.user.info.config;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import org.reactivestreams.Publisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.user.info.entities.LogRequest;

import ch.qos.logback.classic.Level;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class WebResponseFilter implements WebFilter, Ordered {

	private static final ObjectMapper mapper = new ObjectMapper();
	private static final WebClient loggingClient = WebClient.builder().baseUrl("http://localhost:8094/logging-service")
			.build();

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

		String path = exchange.getRequest().getPath().toString();
		ServerHttpResponse response = exchange.getResponse();
		ServerHttpRequest request = exchange.getRequest();
		DataBufferFactory dataBufferFactory = response.bufferFactory();
		ServerHttpResponseDecorator decoratedResponse = getDecoratedResponse(path, response, request,
				dataBufferFactory);
		return chain.filter(exchange.mutate().request(request).response(decoratedResponse).build());
	}

	private ServerHttpResponseDecorator getDecoratedResponse(String path, ServerHttpResponse response,
			ServerHttpRequest request, DataBufferFactory dataBufferFactory) {
		return new ServerHttpResponseDecorator(response) {

			@Override
			public Mono<Void> writeWith(final Publisher<? extends DataBuffer> body) {
				if (body instanceof Flux) {
					Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
					return super.writeWith(fluxBody.buffer().map(dataBuffers -> {

						DefaultDataBuffer joinedBuffers = new DefaultDataBufferFactory().join(dataBuffers);
						byte[] content = new byte[joinedBuffers.readableByteCount()];
						joinedBuffers.read(content);
						String responseBody = new String(content, StandardCharsets.UTF_8);
						logRequestResponse(request, responseBody);
						return dataBufferFactory.wrap(responseBody.getBytes());
					})).onErrorResume(err -> {
						logRequestResponse(request, err);
						return Mono.empty();
					});

				}
				return super.writeWith(body);
			}

		};
	}

	@Override
	public int getOrder() {
		return -2;
	}

	private void logRequestResponse(String request, String response) {
		ObjectNode logRequestObject = mapper.createObjectNode();
		logRequestObject.put("request", request);
		logRequestObject.put("response", response);
		logResponse(logRequestObject.toPrettyString());
	}

	private void logRequestResponse(ServerHttpRequest request, String responseBody) {
		ObjectNode requestObject = mapper.createObjectNode();
		requestObject.put("method", request.getMethod().toString());
		requestObject.put("url", request.getURI().toString());
		requestObject.put("headers", request.getHeaders().toString());
		logRequestResponse(requestObject.toPrettyString(), responseBody);
	}

	private void logRequestResponse(ServerHttpRequest request, Throwable err) {
		ObjectNode errorObject = mapper.createObjectNode();
		errorObject.put("message", err.getMessage());
		errorObject.put("cause", err.getCause().toString());
		errorObject.put("localMessage", err.getLocalizedMessage());
		errorObject.put("class", err.getClass().toGenericString());
		logRequestResponse(request, errorObject.toPrettyString());
	}

	private void logResponse(String logMessage) {
		loggingClient.post().uri("/log")
				.bodyValue(LogRequest.builder().loggerName(getClass().toGenericString()).logLevel(Level.INFO.toString())
						.logMessage(logMessage).logTimestamp(LocalDateTime.now().toString()).build())
				.retrieve().toEntity(String.class).subscribe();
	}
}