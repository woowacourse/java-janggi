package repository;

import domain.Position;
import domain.piece.Piece;

import java.util.Map;

public interface BoardRepository {
    void save(Map<Position, Piece> board);

    Map<Position, Piece> findAll();

    void deleteAll();
}
