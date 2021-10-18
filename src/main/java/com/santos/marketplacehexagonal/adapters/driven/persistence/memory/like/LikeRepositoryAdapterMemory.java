package com.santos.marketplacehexagonal.adapters.driven.persistence.memory.like;

import com.santos.marketplacehexagonal.adapters.driven.persistence.memory.DbPopulate;
import com.santos.marketplacehexagonal.adapters.driven.persistence.memory.InMemoryDb;
import com.santos.marketplacehexagonal.application.entities.Like;
import com.santos.marketplacehexagonal.application.ports.driven.LikeRepositoryPort;
import org.springframework.stereotype.Repository;

@Repository
public class LikeRepositoryAdapterMemory implements LikeRepositoryPort {

    private Integer id;
    private final InMemoryDb inMemoryDb;

    public LikeRepositoryAdapterMemory(InMemoryDb inMemoryDb){
        this.inMemoryDb = inMemoryDb;
        DbPopulate.populate(inMemoryDb);
        id = inMemoryDb.likes.size();
    }

    @Override
    public Like like(Like like) {
        var hasLiked = inMemoryDb.likes.values().stream().anyMatch(l -> l.equals(like));

        if(hasLiked)
            return null;

        like.setId((id++).toString());
        inMemoryDb.likes.put(like.getId(), like);

        return inMemoryDb.likes.get(like.getId());
    }

    @Override
    public Like unlike(Like like) {
        var likeFromDb = inMemoryDb.likes.values().stream().filter(l -> l.equals(like)).findFirst();
        return likeFromDb.map(value -> inMemoryDb.likes.remove(value.getId())).orElse(null);
    }
}
