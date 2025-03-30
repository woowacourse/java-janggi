package janggi.dao;

import janggi.dao.entity.GameEntity;
import janggi.dao.entity.Status;
import janggi.domain.piece.Dynasty;

public interface GameDao {
    GameEntity findByStatus(Status status);

    GameEntity findById(Long gameId);

    void addGame(GameEntity gameEntity);

    void updateCurrentTurn(Long gameId, Dynasty currentTurn);

    void updateStatus(Long gameId, Status status);
}
