package com.santos.marketplacehexagonal.application.services;

import com.santos.marketplacehexagonal.application.entities.Product;
import com.santos.marketplacehexagonal.application.ports.driven.ProductRepository;
import com.santos.marketplacehexagonal.application.ports.driver.ProductServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductServicePort {

    private final ProductRepository productRepository;

    @Override
    public Product createNewProduct(Product product) {



        product.isValid();
        return productRepository.save(product);
    }
}
