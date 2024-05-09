package com.user.info.services;

import org.springframework.stereotype.Service;

import com.user.info.entities.Order;
import com.user.info.entities.Product;
import com.user.info.entities.UserInfoEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface ProductService {

	Mono<Product> findProductByUserId(String userId);
	Mono<String> findPhoneNumberByUserId(String userId);
	Flux<Order> findOrdersByPhoneNumber(String phoneNumber);
	Flux<Product> findProductsByProductCode(String productCode);
	Mono<Product> findProductWithHighestScore(Flux<Product> productFlux);
	Flux<Order> findOrdersByUserId(String userId);
	Flux<Product> findAllProducts();
	Flux<UserInfoEntity> findAllUsers();
	Flux<Order> findAllOrders();
	Flux<Order> findOrdersByPhoneNumber(Flux<String> phoneNumberFlux);
	
}
