package com.griddynamics.reactive.course.ordersearchservice.controller;


import com.griddynamics.reactive.course.ordersearchservice.domain.Order;
import com.griddynamics.reactive.course.ordersearchservice.service.OrderSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RestController
@RequestMapping("/orderInfoService")
@RequiredArgsConstructor
public class OrderSearchController {

    private final OrderSearchService orderSearchService;

    @GetMapping("/order/phone")
    public Flux<Order> getOrderByPhone(@RequestParam String phoneNumber) {
        return orderSearchService.getOrdersByPhone(phoneNumber);
    }
}
