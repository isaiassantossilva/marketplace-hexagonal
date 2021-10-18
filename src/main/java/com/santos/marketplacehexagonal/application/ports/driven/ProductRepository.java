package com.santos.marketplacehexagonal.application.ports.driven;

import com.santos.marketplacehexagonal.application.entities.Product;

public interface ProductRepository {
    Product save(Product product);
}
