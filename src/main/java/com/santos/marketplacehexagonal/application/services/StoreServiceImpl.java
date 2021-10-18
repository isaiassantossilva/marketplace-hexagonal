package com.santos.marketplacehexagonal.application.services;

import com.santos.marketplacehexagonal.application.entities.Like;
import com.santos.marketplacehexagonal.application.entities.Owner;
import com.santos.marketplacehexagonal.application.entities.Store;
import com.santos.marketplacehexagonal.application.entities.User;
import com.santos.marketplacehexagonal.application.ports.driven.LikeRepositoryPort;
import com.santos.marketplacehexagonal.application.ports.driven.StoreRepositoryPort;
import com.santos.marketplacehexagonal.application.ports.driver.StoreServicePort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class StoreServiceImpl implements StoreServicePort {

    private final StoreRepositoryPort storeRepository;
    private final LikeRepositoryPort likeRepositoryPort;

    @Override
    public Store createNewStore(Store store) {
        var storeFromDb =  storeRepository.findStoreByName(store.getName());

        if(storeFromDb != null)
            throw new RuntimeException("Store with name "+ store.getName() +" already exists!");

        store.isValid();
        return storeRepository.save(store);
    }


    @Override
    public Store updateStore(Store store) {
        var user = new User(store.getOwner().getUserId(), store.getOwner().getAccountId());
        var storeFromDb = storeRepository.findStoreById(store.getId(), user);

        if(storeFromDb == null)
            throw new RuntimeException("Store not found!");

        if(!storeFromDb.getOwner().equals(store.getOwner()))
            throw new RuntimeException("Invalid operation!");

        storeFromDb.replace(store);
        storeFromDb.isValid();

        return storeRepository.save(storeFromDb);
    }


    @Override
    public Store findStoreById(String storeId, User user) {
        var storeFromDb = storeRepository.findStoreById(storeId, user);

        if(storeFromDb == null)
            throw new RuntimeException("Store not found!");

        return storeFromDb;
    }


    @Override
    public List<Store> findStoresByOwner(Owner owner) {
        return storeRepository.findStoresByOwner(owner);
    }


    @Override
    public void likeStore(String storeId, User user){
        var store = storeRepository.findStoreById(storeId, user);

        if(store == null)
            throw new RuntimeException("Store not found!");

        var like = new Like(null, store, user, LocalDateTime.now());

        likeRepositoryPort.like(like);
    }

    @Override
    public void unlikeStore(String storeId, User user) {
        var store = storeRepository.findStoreById(storeId, user);

        if(store == null)
            throw new RuntimeException("Store not found!");

        var like = new Like(null, store, user, null);
        likeRepositoryPort.unlike(like);
    }
}
