package player;

import java.util.ArrayList;
import piece.Piece;
import java.util.List;
import pieceProperty.Position;

public class Pieces {
    private final List<Piece> pieces;

    public Pieces(List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }

    public void removePiece(Position destination) {
        pieces.stream()
                .filter(piece -> piece.isSamePosition(destination))
                .findFirst()
                .ifPresent(pieces::remove);
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public boolean isKingDie() {
        return pieces.stream()
                .noneMatch(Piece::isKing);
    }
}
