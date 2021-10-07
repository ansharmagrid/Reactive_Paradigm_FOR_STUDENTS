package com.griddynamics.reactive.course.productinfoservice.resource;

import com.griddynamics.reactive.course.productinfoservice.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductResource {
    public List<Product> getProductNamesByProductCode(String productCode) {
        return List.of(new Product("111", productCode, "IceCream", 98.02),
                new Product("222", productCode, "Milk", 108.75),
                new Product("333", productCode, "Meal", 23.99),
                new Product("444", productCode, "Apple", 47.55));
    }
}
