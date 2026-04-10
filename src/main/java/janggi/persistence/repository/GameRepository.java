package janggi.persistence.repository;

import janggi.domain.game.GameManager;
import janggi.dto.GameSessionDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GameRepository {

    List<GameSessionDTO> findAllGameStatusByFinishedFalse(Connection connection) throws SQLException;

    GameManager save(Connection connection, GameManager gameManager) throws SQLException;

    GameManager findByGameId(Connection connection, long gameId) throws SQLException;
}
