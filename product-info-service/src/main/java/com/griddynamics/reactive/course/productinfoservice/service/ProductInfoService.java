package com.griddynamics.reactive.course.productinfoservice.service;

import com.griddynamics.reactive.course.productinfoservice.domain.Product;
import com.griddynamics.reactive.course.productinfoservice.resource.ProductResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductInfoService {

    private final ProductResource productResource;

    public List<Product> getProductNamesByProductId(String productId) {
        return productResource.getProductNamesByProductId(productId);
    }
}
