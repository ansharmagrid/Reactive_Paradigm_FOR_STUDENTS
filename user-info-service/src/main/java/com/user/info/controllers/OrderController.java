package com.user.info.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.info.entities.Order;
import com.user.info.services.ProductService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/orderDetails")
@RequiredArgsConstructor
@ControllerAdvice
public class OrderController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/all")
	Flux<Order> findAllOrders(){
		return productService.findAllOrders();
	}
}
