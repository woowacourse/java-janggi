package janggi.repository;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface GameStateDao {
    long create(Connection connection, String currentTurn);

    List<Long> findAllIds(Connection connection);

    Optional<String> findCurrentTurn(Connection connection, long gameId);

    void update(Connection connection, long gameId, String currentTurn);

    void delete(Connection connection, long gameId);
}
