package repository;

import domain.Game;
import java.sql.Connection;
import java.util.Optional;

public interface GameRepository {
    Optional<Long> save(Connection connection, Game game);

    Optional<Game> findLatest(Connection connection);
}
