package piece;

import direction.Point;
import java.util.List;
import java.util.Optional;

public class Pieces {

    private final List<Piece> pieces;

    public Pieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public Optional<Piece> findByPoint(Point point) {
        return pieces.stream()
                .filter(piece -> piece.getPosition().equals(point))
                .findAny();
    }

    public List<Piece> getPieces() {
        return pieces;
    }
}
