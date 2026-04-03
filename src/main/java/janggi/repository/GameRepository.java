package janggi.repository;

import janggi.domain.game.Game;
import java.util.Optional;

public interface GameRepository {

    Long save(Game game);

    Optional<Game> findById(Long gameId);

    void update(Long gameId, Game game);

}
