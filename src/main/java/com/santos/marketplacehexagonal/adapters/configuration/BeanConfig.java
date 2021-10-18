package com.santos.marketplacehexagonal.adapters.configuration;

import com.santos.marketplacehexagonal.application.ports.driven.LikeRepositoryPort;
import com.santos.marketplacehexagonal.application.ports.driven.StoreRepositoryPort;
import com.santos.marketplacehexagonal.application.ports.driver.StoreServicePort;
import com.santos.marketplacehexagonal.application.services.StoreServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    StoreServicePort storeService(StoreRepositoryPort storeRepositoryPort, LikeRepositoryPort likeRepositoryPort){
        return new StoreServiceImpl(storeRepositoryPort, likeRepositoryPort);
    }
}
