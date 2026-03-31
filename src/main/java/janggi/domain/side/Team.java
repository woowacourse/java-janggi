package janggi.domain.side;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.dto.BoardSpot;

import java.util.Map;
import java.util.Optional;

public interface Team {

    Map<Position, BoardSpot> makeSnapShot();

    boolean isPieceExists(Position position);

    Optional<Piece> findPiece(Position position);

    Team move(Position start, Position end);

    Team remove(Position position);
}
