package dao;

import domain.board.Point;
import domain.piece.Piece;

import java.util.Map;

public interface BoardDao {

    Map<Point, Piece> load();

    void save(final Point point, final Piece piece);

    void removeAll();
}
