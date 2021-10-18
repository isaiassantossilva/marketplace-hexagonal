package com.santos.marketplacehexagonal.adapters.driven.persistence.memory;

import com.santos.marketplacehexagonal.application.entities.Like;
import com.santos.marketplacehexagonal.application.entities.Store;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryDb {
    public Map<String, Store> stores = new HashMap<>();
    public Map<String, Like> likes = new HashMap<>();
}
