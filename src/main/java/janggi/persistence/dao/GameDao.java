package janggi.persistence.dao;

import janggi.domain.game.GameManager;
import janggi.dto.GameSessionDto;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GameDao {

    long insert(Connection connection, GameManager gameManager) throws SQLException;

    void update(Connection connection, GameManager gameManager) throws SQLException;

    GameSessionDto findById(Connection connection, long gameId) throws SQLException;

    List<GameSessionDto> findAllActive(Connection connection) throws SQLException;
}
