package janggi.domain.side;

import janggi.domain.Pieces;
import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.dto.BoardSpot;
import java.util.List;
import java.util.Optional;

public class Han implements Team {

    private final Pieces pieces;

    private Han(Pieces pieces) {
        this.pieces = pieces;
    }

    public static Han createInitialHan() {
        return new Han(Pieces.createHan());
    }

    @Override
    public List<BoardSpot> makeSnapShot() {
        return pieces.makeSnapShot();
    }

    @Override
    public Optional<Piece> findPiece(Position position) {
        return pieces.findPiece(position);
    }

    @Override
    public Team remove(Position position) {
        return new Han(pieces.remove(position));
    }

    @Override
    public Team move(Position piecePosition, Position targetPosition) {
        return new Han(pieces.move(piecePosition, targetPosition));
    }
}
