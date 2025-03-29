package janggi.repository;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.piece.Piece;

public interface BoardRepository {
    void save(long janggiId, Position departure, Position destination, Piece piece, boolean isAlive);

    void saveAll(long janggiId, Board board);
}
