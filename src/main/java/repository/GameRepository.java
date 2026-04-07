package repository;

import domain.janggigame.JanggiGame;

import java.util.Optional;

public interface GameRepository {
    Long save(JanggiGame janggiGame);

    void update(Long gameId, JanggiGame janggiGame);

    boolean isFinished(Long gameId);

    Optional<String> findCurrentTurnById(Long gameId);
}
