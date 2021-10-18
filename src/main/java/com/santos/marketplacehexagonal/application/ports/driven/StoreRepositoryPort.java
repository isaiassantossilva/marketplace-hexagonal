package com.santos.marketplacehexagonal.application.ports.driven;

import com.santos.marketplacehexagonal.application.entities.Owner;
import com.santos.marketplacehexagonal.application.entities.Store;
import com.santos.marketplacehexagonal.application.entities.User;

import java.util.List;

public interface StoreRepositoryPort {
    List<Store> findStoresByOwner(Owner owner);
    Store save(Store store);

    Store findStoreByName(String storeName);

    Store findStoreById(String storeId, User user);
}
