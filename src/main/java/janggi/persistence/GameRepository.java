package janggi.persistence;

import janggi.domain.game.GameManager;
import janggi.dto.GameSessionDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GameRepository {

    List<GameSessionDTO> findAllActiveGames(Connection connection) throws SQLException;

    long insertGame(Connection connection, GameManager gameManager) throws SQLException;

    GameSessionDTO findByGameId(Connection connection, long gameId) throws SQLException;

    void updateTurn(Connection connection, long gameId, GameManager gameManager) throws SQLException;

    void updateIsFinished(Connection connection, long gameId, boolean finished) throws SQLException;
}
