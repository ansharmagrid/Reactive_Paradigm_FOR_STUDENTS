package com.griddynamics.reactive.course.ordersearchservice.resource;

import com.griddynamics.reactive.course.ordersearchservice.domain.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderResource {
    public List<Order> getOrdersByPhone(String phoneNumber) {
        return List.of(new Order(phoneNumber, phoneNumber, "3852"),
                new Order(phoneNumber, phoneNumber, "5256"),
                new Order(phoneNumber, phoneNumber, "7894"),
                new Order(phoneNumber, phoneNumber, "9822"));
    }
}
