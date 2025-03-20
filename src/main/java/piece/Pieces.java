package piece;

import direction.Point;
import java.util.List;

public class Pieces {

    private final List<Piece> pieces;

    public Pieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public Piece findByPoint(Point point) {
        return pieces.stream()
                .filter(piece -> piece.getPosition().equals(point))
                .findAny()
                .get();
    }

    public boolean isExistPieceIn(Point point) {
        return pieces.stream()
                .anyMatch(piece -> piece.getPosition().equals(point));
    }

    public List<Piece> getPieces() {
        return pieces;
    }
}
