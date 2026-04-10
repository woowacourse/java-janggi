package repository;

import domain.board.Board;

import java.sql.Connection;

public interface BoardDao {

    void saveBoard(Connection con, Long gameId, Board board);

    void deleteByPosition(Connection con, Long gameId, int row, int col);

    void updatePosition(Connection con, Long gameId, int fromRow, int fromCol, int toRow, int toCol);

    Board findByGameId(Long gameId);
}
