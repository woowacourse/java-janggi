package janggi.persistence.dao;

import janggi.domain.game.GameManager;
import janggi.dto.GameSessionDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GameDao {

    long insert(Connection connection, GameManager gameManager) throws SQLException;

    void update(Connection connection, GameManager gameManager) throws SQLException;

    GameSessionDTO findById(Connection connection, long gameId) throws SQLException;

    List<GameSessionDTO> findAllActive(Connection connection) throws SQLException;
}
