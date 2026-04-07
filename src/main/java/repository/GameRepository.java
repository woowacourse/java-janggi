package repository;

import domain.janggigame.JanggiGame;

import java.util.Optional;

public interface GameRepository {
    Long save(JanggiGame janggiGame);

    void update(Long gameId, JanggiGame janggiGame);

    Optional<JanggiGame> findById(Long gameId);

    boolean isFinished(Long gameId);

    String findCurrentTurnById(Long gameId);
}
