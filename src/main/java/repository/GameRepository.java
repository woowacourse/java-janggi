package repository;

import domain.janggigame.JanggiGame;

public interface GameRepository {
    Long save(JanggiGame janggiGame);
    void update(Long gameId, JanggiGame janggiGame);
}
