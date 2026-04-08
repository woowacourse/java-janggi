package janggi.repository;

import java.util.Optional;

import janggi.domain.game.Game;
import janggi.domain.side.Side;

public interface GameRepository {

    Optional<Integer> findActiveGameId();

    int save(Game game);

    void update(int gameId, Game game);

    Game load(int gameId);

    void finish(int gameId, Side winner);

}
