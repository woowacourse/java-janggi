package janggi.domain.side;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.dto.BoardSpot;
import java.util.List;
import java.util.Optional;

public interface Team {

    List<BoardSpot> makeSnapShot();

    Optional<Piece> findPiece(Position position);

    Team remove(Position position);

    Team move(Position piecePosition, Position targetPosition);
}
