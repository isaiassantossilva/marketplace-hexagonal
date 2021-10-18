package com.santos.marketplacehexagonal.application.ports.driven;

import com.santos.marketplacehexagonal.application.entities.Like;

public interface LikeRepositoryPort {
    Like like(Like like);
    Like unlike(Like like);
}
