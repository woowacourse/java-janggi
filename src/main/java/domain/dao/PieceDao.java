package domain.dao;

import domain.piece.Piece;
import domain.piece.Position;
import java.util.List;
import java.util.Optional;

public interface PieceDao {

    void addAll(Long gameId, final List<Piece> pieces);

    void add(Long gameId, final Piece piece);

    void removeByPosition(Long gameId, final Position position);

    Optional<Piece> findByPosition(Long gameId, final Position position);

    List<Piece> findAll(Long gameId);

    void removeAll(Long gameId);

    void changePosition(Long gameId, final Position position, final Position newPosition);
}
