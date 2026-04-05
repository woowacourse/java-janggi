package janggi.persistence;

import janggi.domain.game.GameManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public interface GameRepository {

    Optional<Long> findActiveGameId(Connection connection) throws SQLException;

    long insertGame(Connection connection, GameManager gameManager) throws SQLException;

    GameManager findByGameId(Connection connection, long gameId) throws SQLException;
}
