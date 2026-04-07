package repository;

import domain.janggigame.JanggiGame;

import java.sql.Connection;
import java.util.Optional;

public interface GameRepository {
    Long save(Connection connection, JanggiGame janggiGame);

    void update(Connection connection, Long gameId, JanggiGame janggiGame);

    boolean isFinished(Long gameId);

    Optional<String> findCurrentTurnById(Long gameId);
}
