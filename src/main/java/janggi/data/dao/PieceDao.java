package janggi.data.dao;

import janggi.board.point.Point;
import janggi.piece.Piece;

public interface PieceDao {

    void save(Point point, Piece piece);

    void move(Point from, Point to);

    void delete(Point point);
}
