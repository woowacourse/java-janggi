package janggi.persistence;

import janggi.domain.board.Board;
import java.sql.Connection;
import java.sql.SQLException;

public interface BoardRepository {

    Board findAllByGameId(Connection connection, long gameId) throws SQLException;

    void insertBoard(Connection connection, long gameId, Board board) throws SQLException;

    void updateBoard(Connection connection, long gameId, Board board) throws SQLException;
}
