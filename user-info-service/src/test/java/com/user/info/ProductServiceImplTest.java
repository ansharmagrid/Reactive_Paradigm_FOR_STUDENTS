package com.user.info;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.user.info.respositories.UserInfoRepository;
import com.user.info.services.ProductService;

import reactor.test.StepVerifier;

@SpringBootTest
public class ProductServiceImplTest {

	@Autowired
	ProductService productService;

	@Autowired
	UserInfoRepository userInfoRepository;

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

}
