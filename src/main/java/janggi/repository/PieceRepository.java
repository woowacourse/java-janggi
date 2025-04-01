package janggi.repository;

import janggi.domain.piece.Piece;
import janggi.domain.piece.direction.Position;
import java.util.List;

public interface PieceRepository {

    Long add(Piece Piece);

    void addAll(List<Piece> pieces);

    List<Piece> findAll();

    void deleteAll();

    void delete(Position position);

    boolean exist();
}
