package janggi.domain.side;

import janggi.domain.Pieces;
import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.dto.BoardSpot;
import java.util.List;
import java.util.Optional;

public class Chu implements Team {

    private final Pieces pieces;

    private Chu(Pieces pieces) {
        this.pieces = pieces;
    }

    public static Chu createInitialChu() {
        return new Chu(Pieces.createChu());
    }

    @Override
    public boolean isPieceExists(int x, int y) {
        return pieces.isPieceExists(x, y);
    }

    @Override
    public List<BoardSpot> makeSpots() {
        return pieces.makeSpots();
    }

    @Override
    public Optional<Piece> findPiece(Position position) {
        return pieces.findPiece(position);
    }

    @Override
    public Team remove(Position position) {
        return new Chu(pieces.remove(position));
    }

    @Override
    public Team move(Position piecePosition, Position targetPosition) {
        return new Chu(pieces.move(piecePosition, targetPosition));
    }

    @Override
    public void isPieceCanMove(Position startPosition, Position endPosition) {
        pieces.checkPieceCanMove(startPosition, endPosition);
    }
}
