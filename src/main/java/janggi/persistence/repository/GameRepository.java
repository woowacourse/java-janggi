package janggi.persistence.repository;

import janggi.domain.game.GameManager;
import janggi.dto.GameSessionDto;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GameRepository {

    List<GameSessionDto> findAllGameStatusByFinishedFalse(Connection connection) throws SQLException;

    GameManager save(Connection connection, GameManager gameManager) throws SQLException;

    GameManager findByGameId(Connection connection, long gameId) throws SQLException;
}
