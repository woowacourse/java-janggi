package janggi.domain.board.dao;

import janggi.domain.board.JanggiBoard;
import janggi.domain.piece.Piece;
import janggi.domain.value.JanggiPosition;
import java.util.List;

public interface JanggiBoardDAO {
    void insertPieces(final JanggiBoard janggiBoard);
    void dropTables();
    void updateRecords(final JanggiPosition current, final JanggiPosition destination, final int teamId);
    void deleteRecords(final JanggiPosition destination, final int teamId);
    List<Piece> selectChoRecords();
    List<Piece> selectHanRecords();
}
