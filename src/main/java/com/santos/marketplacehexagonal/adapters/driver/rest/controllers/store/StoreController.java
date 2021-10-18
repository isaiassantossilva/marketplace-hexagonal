package com.santos.marketplacehexagonal.adapters.driver.rest.controllers.store;

import com.santos.marketplacehexagonal.application.entities.Category;
import com.santos.marketplacehexagonal.application.entities.Owner;
import com.santos.marketplacehexagonal.application.entities.Store;
import com.santos.marketplacehexagonal.application.entities.User;
import com.santos.marketplacehexagonal.application.ports.driver.StoreServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreServicePort storeService;

    String userId = "U_1";
    String accountId = "A_1";

    @GetMapping("/my")
    public List<Store> findStoresByOwner(){
        var owner = new Owner(userId, accountId);
        return storeService.findStoresByOwner(owner);
    }


    @PostMapping
    public Store createNewStore(@RequestBody Store store){
        var owner = new Owner(userId, accountId);
        var category = new Category("1", "livros");

        store.setOwner(owner);
       store.setCategories(Set.of(category));

        return storeService.createNewStore(store);
    }


    @GetMapping("/{storeId}")
    public Store findStoreById(@PathVariable String storeId){
        return storeService.findStoreById(storeId, new User(userId, accountId));
    }


    @PatchMapping("/{storeId}/like")
    public void likeStore(@PathVariable String storeId){
        storeService.likeStore(storeId, new User(userId, accountId));
    }


    @PatchMapping("/{storeId}/unlike")
    public void unlikeStore(@PathVariable String storeId){
        storeService.unlikeStore(storeId, new User(userId, accountId));
    }
}
