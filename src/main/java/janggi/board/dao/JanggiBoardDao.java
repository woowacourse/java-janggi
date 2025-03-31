package janggi.board.dao;

import janggi.board.JanggiBoard;
import janggi.piece.Piece;
import janggi.value.JanggiPosition;
import java.util.List;

public interface JanggiBoardDao {

    void insertPieces(final JanggiBoard janggiBoard);

    void dropTables();

    void updateRecords(JanggiPosition current, JanggiPosition destination, int teamId);

    void deleteRecords(JanggiPosition destination, int teamId);

    List<Piece> selectChoRecords();

    List<Piece> selectHanRecords();
}
