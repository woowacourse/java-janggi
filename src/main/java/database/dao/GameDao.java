package database.dao;

import database.entity.GameEntity;
import domain.game.Team;
import java.util.Optional;

public interface GameDao {
    int createGame(Team initialTurn);

    Optional<GameEntity> findById(int gameId);

    void updateTurn(int gameId, Team team);

    void deleteById(int id);
}
