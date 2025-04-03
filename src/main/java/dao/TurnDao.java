package dao;

import entity.TurnEntity;

import java.util.Optional;

public interface TurnDao {
    void deleteAll();

    void save(TurnEntity turnEntity);

    Optional<TurnEntity> findTurn();
}
