package janggi.infra.dao;

import janggi.infra.entity.GameEntity;

import java.util.List;

public interface GameDAO {

    Long save(GameEntity gameEntity);

    List<GameEntity> findAllOrderByLastPlayedAtDESC();

}
