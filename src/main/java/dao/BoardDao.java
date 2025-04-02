package dao;

import domain.board.Point;
import domain.piece.Piece;

import java.sql.Connection;
import java.util.Map;

public interface BoardDao {

    boolean hasRecords();

    Map<Point, Piece> load(final int boardId);

    void save(final Connection connection, final Point point, final Piece piece, final int boardId);

    void remove(final Connection connection, final int boardId);

    void removeAll(final Connection connection);
}
