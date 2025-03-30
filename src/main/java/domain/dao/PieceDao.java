package domain.dao;

import domain.piece.Piece;
import domain.piece.Position;
import java.util.List;
import java.util.Optional;

public interface PieceDao {

    void addAll(final List<Piece> pieces);

    void add(final Piece piece);

    void removeByPosition(final Position position);

    Optional<Piece> findByPosition(final Position position);

    List<Piece> findAll();

    void changePosition(final Position position, final Position newPosition);
}
