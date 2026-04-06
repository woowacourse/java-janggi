package janggi.persistence;

import janggi.domain.board.Board;
import janggi.dto.PiecePositionSnapshot;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BoardRepository {

    Board findAllByGameId(Connection connection, long gameId) throws SQLException;

    void insertBoard(Connection connection, long gameId, List<PiecePositionSnapshot> snapshots) throws SQLException;

    void updateBoard(Connection connection, long gameId, List<PiecePositionSnapshot> snapshots) throws SQLException;
}
