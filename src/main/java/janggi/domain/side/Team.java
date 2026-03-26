package janggi.domain.side;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.dto.BoardSpot;
import java.util.List;
import java.util.Optional;

public interface Team {

    boolean isPieceExists(int x, int y);

    List<BoardSpot> makeSpots();

    Optional<Piece> findPiece(Position position);

    Team remove(Position position);

    Team move(Position piecePosition, Position targetPosition);

    void isPieceCanMove(Position startPosition, Position endPosition);


}
