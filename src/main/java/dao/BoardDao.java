package dao;

import domain.board.Point;
import domain.piece.Piece;

import java.sql.Connection;
import java.util.Map;

public interface BoardDao {

    boolean hasRecords();

    Map<Point, Piece> load();

    void save(final Connection connection, final Point point, final Piece piece);

    void removeAll(final Connection connection);
}
