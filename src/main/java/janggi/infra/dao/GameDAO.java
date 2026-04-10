package janggi.infra.dao;

import janggi.infra.entity.GameEntity;

import java.util.List;
import java.util.Optional;

public interface GameDAO {

    Long save(GameEntity gameEntity);

    Optional<GameEntity> findById(Long id);

    List<GameEntity> findAllOrderByLastPlayedAtDesc();

    void updateCurrentTurnAndLastPlayedAt(GameEntity gameEntity);
}
