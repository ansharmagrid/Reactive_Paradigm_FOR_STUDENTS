package com.user.info.integration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.user.info.entities.Product;
import com.user.info.entities.UserInfoEntity;
import com.user.info.respositories.UserInfoRepository;
import com.user.info.services.ProductService;
import com.user.info.util.TestConstants;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringJUnitConfig
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ProductServiceImplTest_WireMockStubbed {

	private static final String USERID_1 = "test-user-1";
	private static final String USERID_2 = "test-user-2";
	private static final String USERNAME_1 = "test-user-name-1";
	private static final String USERNAME_2 = "test-user-name-2";
	private static final String PHONE_1 = "91987654321";
	private static final String PHONE_2 = "91897654321";

	private static final UserInfoEntity USER1 = new UserInfoEntity(USERID_1, USERNAME_1, PHONE_1);
	private static final UserInfoEntity USER2 = new UserInfoEntity(USERID_2, USERNAME_2, PHONE_2);

	private static WireMockServer productInfoService;
	private static WireMockServer orderInfoService;

	@Autowired
	ProductService productService;

	@Autowired
	UserInfoRepository userInfoRepository;

	@BeforeAll
	public static void setup() {
		productInfoService = new WireMockServer(8092); // Port for service 1
		orderInfoService = new WireMockServer(8091); // Port for service 2
		productInfoService.start();
		orderInfoService.start();

	}

	@BeforeEach
	public void mockUnImplementedEndpoints() {

		mockOrderInfoService("91987654321", TestConstants.ORDER_SEARCH_SERVICE_RESPONSE_91987654321);
		mockOrderInfoService("91897654321", TestConstants.ORDER_SEARCH_SERVICE_RESPONSE_91897654321);
		mockProductInfoService("3852", TestConstants.PRODUCT_INFO_SERVICE_RESPONSE_3852);
		mockProductInfoService("5256", TestConstants.PRODUCT_INFO_SERVICE_RESPONSE_5256);
		mockProductInfoService("7894", TestConstants.PRODUCT_INFO_SERVICE_RESPONSE_7894);
		mockProductInfoService("9822", TestConstants.PRODUCT_INFO_SERVICE_RESPONSE_9822);

	}

	private void mockOrderInfoService(String phoneNumber, String expectedResponse) {
		orderInfoService.stubFor(get(urlMatching(".*orderSearchService/order/phone.*"))
				.withQueryParam("phoneNumber", equalTo(phoneNumber))
				.willReturn(aResponse().withStatus(200).withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
						.withBody(expectedResponse)));
	}

	private void mockProductInfoService(String productCode, String expectedResponse) {
		productInfoService.stubFor(get(urlMatching(".*productInfoService/product/names.*"))
				.withQueryParam("productCode", equalTo(productCode)).willReturn(aResponse().withStatus(200)
						.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE).withBody(expectedResponse)));
	}

	@Test
	public void a_insertTestRecords() {
		userInfoRepository.deleteAll();
		StepVerifier.create(userInfoRepository.save(USER1)).expectNext(USER1).verifyComplete();
		StepVerifier.create(userInfoRepository.save(USER2)).expectNext(USER2).verifyComplete();
	}

	@Test
	public void b_getTestUsers() {
		StepVerifier.create(userInfoRepository.findById(USERID_1)).expectNext(USER1).verifyComplete();
		StepVerifier.create(userInfoRepository.findById(USERID_2)).expectNext(USER2).verifyComplete();
	}

	@Test
	public void c_findAllOrders() {
		StepVerifier.create(productService.findAllOrders()).expectNextCount(8).verifyComplete();
	}

	@Test
	public void d_findAllProducts() {
		StepVerifier.create(productService.findAllProducts()).expectNextCount(32).verifyComplete();
	}

	@Test
	public void e_findAllUsers() {
		StepVerifier.create(productService.findAllUsers()).expectNextCount(2).verifyComplete();
	}

	@Test
	public void f_findOrdersByPhoneNumberUsingStringFlux() {
		StepVerifier.create(productService.findOrdersByPhoneNumber(Flux.fromIterable(List.of(PHONE_1))))
				.expectNextCount(4).verifyComplete();
	}

	@Test
	public void g_findOrdersByPhoneNumberUsingString() {
		StepVerifier.create(productService.findOrdersByPhoneNumber(PHONE_1)).expectNextCount(4).verifyComplete();
	}

	@Test
	public void h_findOrdersByUserId() {
		StepVerifier.create(productService.findOrdersByUserId(USERID_1)).expectNextCount(4).verifyComplete();
	}

	@Test
	public void i_findPhoneNumberByUserId() {
		StepVerifier.create(productService.findPhoneNumberByUserId(USERID_1)).expectNext(PHONE_1).verifyComplete();
	}

	@Test
	public void j_findProductByUserId() {
		StepVerifier.create(productService.findProductByUserId(USERID_1)).expectNextCount(1).verifyComplete();
	}

	@Test
	public void k_findProductsByProductCode() {
		StepVerifier.create(productService.findProductsByProductCode("3852")).expectNextCount(4).verifyComplete();
	}

	@Test
	public void l_findProductWithHighestScore() {
		Flux<Product> productFlux = Flux
				.fromIterable(List.of(new Product("product-id-1", "product-code", PHONE_1, 123.0),
						new Product("product-id-2", "product-code", PHONE_1, 789.0),
						new Product("product-id-3", "product-code", PHONE_1, 346.0),
						new Product("product-id-4", "product-code", PHONE_1, 256.0),
						new Product("product-id-5", "product-code", PHONE_1, 912.0),
						new Product("product-id-6", "product-code", PHONE_1, 325.0),
						new Product("product-id-7", "product-code", PHONE_1, 567.0),
						new Product("product-id-8", "product-code", PHONE_1, 987.0),
						new Product("product-id-9", "product-code", PHONE_1, 123.0)));
		StepVerifier.create(productService.findProductWithHighestScore(productFlux))
				.expectNext(new Product("product-id-8", "product-code", PHONE_1, 987.0)).verifyComplete();
	}

	@Test
	public void z_clearTestUsers() {
		StepVerifier.create(userInfoRepository.deleteById(USERID_1)).expectNextCount(0).verifyComplete();
		StepVerifier.create(userInfoRepository.deleteById(USERID_2)).expectNextCount(0).verifyComplete();
	}

	@AfterAll
	public static void tearDown() {
		productInfoService.stop();
		orderInfoService.stop();
	}
}
