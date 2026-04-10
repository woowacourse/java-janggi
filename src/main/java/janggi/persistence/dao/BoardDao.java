package janggi.persistence.dao;

import janggi.domain.board.Board;
import java.sql.Connection;
import java.sql.SQLException;

public interface BoardDao {

    void deleteByGameId(Connection connection, long gameId) throws SQLException;

    void insertAll(Connection connection, long gameId, Board board) throws SQLException;

    Board findByGameId(Connection connection, long gameId) throws SQLException;
}
