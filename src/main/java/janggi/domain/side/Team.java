package janggi.domain.side;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.dto.BoardSpots;
import java.util.Optional;

public interface Team {

    BoardSpots makeSnapShot();

    Optional<Piece> findPiece(Position position);

    Team remove(Position position);

    Team move(Position piecePosition, Position targetPosition);
}
