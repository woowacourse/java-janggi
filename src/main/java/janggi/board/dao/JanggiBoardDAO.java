package janggi.board.dao;

import janggi.board.JanggiBoard;
import janggi.piece.Piece;
import janggi.value.JanggiPosition;
import java.util.List;

public interface JanggiBoardDAO {
    void insertPieces(final JanggiBoard janggiBoard);
    void dropTables();
    void updateRecords(final JanggiPosition current, final JanggiPosition destination, final int teamId);
    void deleteRecords(final JanggiPosition destination, final int teamId);
    List<Piece> selectChoRecords();
    List<Piece> selectHanRecords();
}
