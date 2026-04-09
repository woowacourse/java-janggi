package janggi.repository.game;

import janggi.domain.game.GameState;
import janggi.entity.GameEntity;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface GameRepository {

    Long save(Connection connection, GameEntity game);

    void update(Connection connection, Long gameId, GameEntity game);

    Optional<GameEntity> findById(Long gameId);

    List<Long> findAllByState(GameState state);
}