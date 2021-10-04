package com.griddynamics.reactive.course.productinfoservice.resource;

import com.griddynamics.reactive.course.productinfoservice.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.griddynamics.reactive.course.productinfoservice.util.Utils.delay;


@Repository
public class ProductResource {
    public List<Product> getProductNamesByProductId(String productId) {
        delay(1000);
        return List.of(new Product("111", "IceCream", 98.02),
                new Product("222", "Milk", 108.75),
                new Product("333", "Meal", 23.99),
                new Product("444", "Apple", 47.55));
    }
}
