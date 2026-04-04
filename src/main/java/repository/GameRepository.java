package repository;

import domain.Game;

public interface GameRepository {

    void save(Game game);
    Game load(Long gameId);
}
