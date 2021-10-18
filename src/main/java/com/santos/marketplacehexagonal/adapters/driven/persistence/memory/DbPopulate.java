package com.santos.marketplacehexagonal.adapters.driven.persistence.memory;

import com.santos.marketplacehexagonal.application.entities.Category;
import com.santos.marketplacehexagonal.application.entities.Owner;
import com.santos.marketplacehexagonal.application.entities.Store;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DbPopulate {
    public static void populate(InMemoryDb inMemoryDb){
        inMemoryDb.stores = storePopulate();
    }

    private static Map<String, Store> storePopulate(){
        Map<String, Store> stores = new HashMap<>();

        var store1 = new Store(
                "0",
                new Owner("U_1", "A_1"),
                "Amazon Store",
                "All from A to Z",
                "http://logo",
                "0xFFF",
                Set.of(new Category("1", "e-books"))
        );

        var store2 = new Store(
                "1",
                new Owner("U_2", "A_1"),
                "Facebook",
                "Social Media",
                "http://logo",
                "0xFFF",
                Set.of(new Category("2", "games"))
        );

        var store3 = new Store(
                "2",
                new Owner("U_1", "A_1"),
                "Ifood",
                "Rede de comida",
                "http://logo",
                "0xFFF",
                Set.of(new Category("3", "comidas"))
        );

        stores.put(store1.getId(), store1);
        stores.put(store2.getId(), store2);
        stores.put(store3.getId(), store3);

        return stores;
    }
}
