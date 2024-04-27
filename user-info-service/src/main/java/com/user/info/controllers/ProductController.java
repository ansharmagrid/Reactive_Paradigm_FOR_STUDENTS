package com.user.info.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.info.entities.Product;
import com.user.info.services.ProductService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/productDetails")
@RequiredArgsConstructor
@ControllerAdvice
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/user/{userId}")
	Mono<Product> findProductByUserId(@PathVariable("userId") String userId){
		return productService.findProductByUserId(userId);
	}
	
	@GetMapping("/all")
	Flux<Product> findAllProducts(){
		return productService.findAllProducts();
	}
	
}
