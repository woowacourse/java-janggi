package janggi.domain.piece.pieces;

import janggi.domain.piece.PieceType;
import janggi.domain.piece.PieceView;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PiecesView {

    boolean isEnemyOnDestination(Side side, Position destination);

    PiecesView getPiecesOnPath(Collection<Position> pathsToDestination);

    boolean hasOnlyOnePiece();

    PiecesView getMapWithoutPosition(Position position);

    Optional<PieceView> findByPosition(Position position);

    int size();

    boolean isEmpty();

    int countByPieceType(PieceType pieceType);

    List<Side> findAllByPieceType(PieceType pieceType);

    boolean containsPieceType(PieceType pieceType);

    boolean isAllyOnDestination(Side side, Position destination);
}
