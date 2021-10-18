package com.santos.marketplacehexagonal.application.ports.driver;

import com.santos.marketplacehexagonal.application.entities.Owner;
import com.santos.marketplacehexagonal.application.entities.Store;
import com.santos.marketplacehexagonal.application.entities.User;

import java.util.List;

public interface StoreServicePort {
    Store createNewStore(Store store);
    Store updateStore(Store store);
    Store findStoreById(String storeId, User user);
    List<Store> findStoresByOwner(Owner owner);
    void likeStore(String storeId, User user);
    void unlikeStore(String storeId, User user);
}
