package com.user.info;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.user.info.entities.UserInfoEntity;
import com.user.info.respositories.UserInfoRepository;
import com.user.info.services.ProductService;

import reactor.test.StepVerifier;

@SpringBootTest
public class ProductServiceImplTest {

	/*
	  	Mono<Product> findProductByUserId(String parseLong);
		Mono<String> findPhoneNumberByUserId(String userId);
		Flux<Order> findOrdersByPhoneNumber(String phoneNumber);
		Flux<Product> findProductsByProductCode(String productCode);
		Mono<Product> findProductWithHighestScore(Flux<Product> productFlux);
		Flux<Order> findOrdersByUserId(String userId);
		Flux<Product> findAllProducts();
		Flux<UserInfoEntity> findAllUsers();
		Flux<Order> findAllOrders();
		Flux<Order> findOrdersByPhoneNumber(Flux<String> phoneNumberFlux);
	
	 */
	@Autowired
	ProductService productService;

	@Autowired
	UserInfoRepository userInfoRepository;
	
	@Test
	public void findProductByUserIdTest() {
		StepVerifier.create(productService.findProductByUserId("user1"));
	}

	@Test
	public void findOrdersByPhoneNumberTest() {
		StepVerifier.create(productService.findOrdersByPhoneNumber("12345678")).assertNext(order -> {
			assertThat(order).hasFieldOrProperty("productCode");
		}).thenConsumeWhile(order -> true).verifyComplete();
	}

	@Test
	public void findPhoneNumbersByUserIdTest() {
		StepVerifier.create(productService.findPhoneNumberByUserId("user1")).assertNext(phone -> {
			assertThat(phone).isNotBlank();
		}).thenConsumeWhile(phone -> true).verifyComplete();

	}
	
	@Test
	public void insertDummyRecords() {
		userInfoRepository.save(new UserInfoEntity("user1", "John", "123456789")).block();
		userInfoRepository.save(new UserInfoEntity("user2", "Mark", "987654321")).block();
	}

}
