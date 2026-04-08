package janggi.domain.repository;

import janggi.domain.Game;
import java.util.Optional;

public interface GameRepository {

    Long save(Game game);

    Optional<Game> findById(Long gameId);
}
