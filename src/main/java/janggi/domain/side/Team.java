package janggi.domain.side;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.dto.BoardSpot;
import janggi.dto.BoardSpots;

import java.util.Map;
import java.util.Optional;

public interface Team {

    Map<Position, BoardSpot> makeSnapShot();

    boolean isPieceExist(Position position);

    Optional<Piece> findPiece(Position position);

    Team remove(Position position);

    Team move(Position piecePosition, Position targetPosition);
}
