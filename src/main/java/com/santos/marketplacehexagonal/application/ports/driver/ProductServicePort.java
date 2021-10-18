package com.santos.marketplacehexagonal.application.ports.driver;

import com.santos.marketplacehexagonal.application.entities.Product;

public interface ProductServicePort {
    Product createNewProduct(Product product);
}
