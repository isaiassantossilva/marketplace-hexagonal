package com.santos.marketplacehexagonal.application.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.santos.marketplacehexagonal.adapters.driven.persistence.memory.InMemoryDb;
import com.santos.marketplacehexagonal.adapters.driven.persistence.memory.like.LikeRepositoryAdapterMemory;
import com.santos.marketplacehexagonal.adapters.driven.persistence.memory.store.StoreRepositoryAdapterMemory;
import com.santos.marketplacehexagonal.application.entities.Category;
import com.santos.marketplacehexagonal.application.entities.Owner;
import com.santos.marketplacehexagonal.application.entities.Store;
import com.santos.marketplacehexagonal.application.entities.User;
import com.santos.marketplacehexagonal.application.ports.driven.LikeRepositoryPort;
import com.santos.marketplacehexagonal.application.ports.driven.StoreRepositoryPort;
import com.santos.marketplacehexagonal.application.ports.driver.StoreServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StoreServiceTest {

    StoreRepositoryPort storeRepository;
    LikeRepositoryPort likeRepositoryPort;
    StoreServicePort storeService;
    InMemoryDb inMemoryDb;

    @BeforeEach
    void setup(){
        inMemoryDb = new InMemoryDb();
        storeRepository = new StoreRepositoryAdapterMemory(inMemoryDb);
        likeRepositoryPort = new LikeRepositoryAdapterMemory(inMemoryDb);
        storeService = new StoreServiceImpl(storeRepository, likeRepositoryPort);
    }

    @Test
    void shouldReturnStoreByOwnerWhenItIsGiven() {
        var stores = storeService.findStoresByOwner(new Owner("U_1", "A_1"));
        assertNotNull(stores);
        assertEquals(2, stores.size());
    }


    @Test
    void shouldCreateAStore() {
        var owner = new Owner("U_1", "A_1");
        var storeToSave = new Store(
                null,
                new Owner("U_1", "A_1"),
                "My Store",
                "All from A to Z",
                "http://logo",
                "0xFFF",
                Set.of(new Category("1", "e-books"))
        );


        var storesListBeforeSaved = storeService.findStoresByOwner(owner);
        assertEquals(2, storesListBeforeSaved.size());

        var savedStore = storeService.createNewStore(storeToSave);

        var storesListAfterSaved = storeService.findStoresByOwner(owner);
        assertEquals(3, storesListAfterSaved.size());

        assertEquals(savedStore.getName(), savedStore.getName());
    }


    @Test
    void shouldThrowsAExceptionWhenAStoreWithGivenNameAlreadyExists(){
        var storeToSave = new Store(
                null,
                new Owner("U_1", "A_1"),
                "My Store",
                "All from A to Z",
                "http://logo",
                "0xFFF",
                Set.of(new Category("1", "e-books"))
        );

        assertThrows(RuntimeException.class, () -> {
            storeService.createNewStore(storeToSave);
            storeService.createNewStore(storeToSave);
        });

        try {
            storeService.createNewStore(storeToSave);
            storeService.createNewStore(storeToSave);
        } catch (Exception e){
            assertEquals("Store with name "+ storeToSave.getName() +" already exists!", e.getMessage());
        }
    }


    @Test
    void shouldUpdateAStoreWhenSuccess() {
        var gson = new Gson();

        var owner = new Owner("U_1", "A_1");

        var stores = storeService.findStoresByOwner(owner);
        Store storeBeforeUpdate = gson.fromJson(gson.toJson(stores.get(0)), Store.class);

        var storeToUpdate = new Store();
        storeToUpdate.setId(storeBeforeUpdate.getId());
        storeToUpdate.setName("Store After Update");
        storeToUpdate.setOwner(owner);

        var storeAfterUpdate = storeService.updateStore(storeToUpdate);

        assertNotEquals(storeAfterUpdate.getName(), storeBeforeUpdate.getName());
        assertEquals(storeBeforeUpdate.getId(), storeAfterUpdate.getId());
    }


    @Test
    void shouldThrowsExceptionWhenUserNotBeStoreOwner() {
        var owner = new Owner("U_1", "A_1");

        var storeToUpdate = new Store();
        storeToUpdate.setId("1");
        storeToUpdate.setName("Store After Update");
        storeToUpdate.setOwner(owner);

        assertThrows(RuntimeException.class, () -> {
            storeService.updateStore(storeToUpdate);
        });

        try{
            storeService.updateStore(storeToUpdate);
        }catch (Exception e){
            assertEquals(e.getMessage(), "Invalid operation!");
        }
    }


    @Test
    void shouldReturnStoreWhenGivenAStoreId(){
        var store = storeService.findStoreById("1", new User("A_1", "U_1"));
        assertNotNull(store);
    }


    @Test
    void shouldThrowsAExceptionWhenGivenAStoreIdThatNotExists(){

        assertThrows(RuntimeException.class, ()-> {
            storeService.findStoreById("100", new User("A_1", "U_1"));
        });

        try {
            storeService.findStoreById("100", new User("A_1", "U_1"));
        } catch (Exception e){
            assertEquals(e.getMessage(), "Store not found!");
        }
    }


    @Test
    void shouldToLikeAStoreWhenSuccess(){
        storeService.likeStore("1", new User("U_1", "A_1"));
        storeService.likeStore("1", new User("U_2", "A_1"));

        var store = storeService.findStoreById("1", new User("U_1", "A_1"));

        assertNotNull(store);
        assertNotNull(store.getLiked());
        assertTrue(store.getLiked());

        store = storeService.findStoreById("1", new User("U_3", "A_1"));
        assertFalse(store.getLiked());

        assertEquals(2, inMemoryDb.likes.size());
    }


    @Test
    void shouldThrowsAExceptionWhenGiveAIdThatNotExists(){
        assertThrows(RuntimeException.class, () -> storeService.likeStore("100", new User("U_1", "A_1")));

        try {
            storeService.likeStore("100", new User("U_1", "A_1"));
        } catch (Exception e){
            assertEquals(e.getMessage(), "Store not found!");
        }
    }


    @Test
    void shouldNotLikeAStoreTwoTimes(){
        storeService.likeStore("1", new User("U_1", "A_1"));
        storeService.likeStore("1", new User("U_1", "A_1"));

        assertEquals(1, inMemoryDb.likes.size());

        storeService.likeStore("1", new User("U_2", "A_1"));

        assertEquals(2, inMemoryDb.likes.size());
    }


    @Test
    void shouldUnlikeAStore(){
        assertEquals(0, inMemoryDb.likes.size());
        storeService.likeStore("1", new User("U_1", "A_1"));
        assertEquals(1, inMemoryDb.likes.size());
        storeService.unlikeStore("1", new User("U_1", "A_1"));
        assertEquals(0, inMemoryDb.likes.size());
    }
}