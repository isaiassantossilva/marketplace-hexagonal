package com.santos.marketplacehexagonal.adapters.driven.persistence.memory.store;

import com.santos.marketplacehexagonal.adapters.driven.persistence.memory.DbPopulate;
import com.santos.marketplacehexagonal.adapters.driven.persistence.memory.InMemoryDb;
import com.santos.marketplacehexagonal.application.entities.*;
import com.santos.marketplacehexagonal.application.ports.driven.StoreRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class StoreRepositoryAdapterMemory implements StoreRepositoryPort {

    private Integer id;
    private final InMemoryDb inMemoryDb;

    public StoreRepositoryAdapterMemory(InMemoryDb inMemoryDb){
        this.inMemoryDb = inMemoryDb;
        DbPopulate.populate(inMemoryDb);
        id = inMemoryDb.stores.size();
    }


    @Override
    public List<Store> findStoresByOwner(Owner owner) {
        return inMemoryDb.stores
                .values()
                .stream()
                .filter(s -> s.getOwner().equals(owner))
                .collect(Collectors.toList());
    }


    @Override
    public Store save(Store store) {
        if(store.getId() != null)
            return update(store);

        return create(store);
    }

    private Store create(Store store) {
        store.setId((id++).toString());
        inMemoryDb.stores.put(store.getId(), store);
        return inMemoryDb.stores.get(store.getId());
    }

    private Store update(Store store) {
        inMemoryDb.stores.put(store.getId(), store);
        return inMemoryDb.stores.get(store.getId());
    }


    @Override
    public Store findStoreByName(String storeName) {
        var store = inMemoryDb.stores
                .values()
                .stream()
                .filter(s -> s.getName().equals(storeName))
                .findFirst();

        return store.orElse(null);
    }


    @Override
    public Store findStoreById(String storeId, User user) {
        var store = inMemoryDb.stores.get(storeId);

        if(store == null)
            return null;


        var numberOfLikes = inMemoryDb.likes.values().stream().filter(l -> {
            if(l.getEntity() instanceof Store)
                return ((Store) l.getEntity()).getId().equals(storeId);
            return false;
        }).count();


        var liked = inMemoryDb.likes.values().stream().filter(l -> l.equals(new Like(null, store, user, null))).findFirst();

        store.setLiked(liked.isPresent());
        store.setNumberOfLikes(numberOfLikes);

        return store;
    }
}
