package domain.piece;

import java.util.Collections;
import java.util.List;

public class Pieces {

    private final List<Piece> pieces;

    public Pieces(final List<Piece> pieces) {
        this.pieces = pieces;
    }

    public Piece findByPosition(final Position position) {
        return pieces.stream()
                .filter(element -> element.getPosition().equals(position))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<Piece> getPieces() {
        return Collections.unmodifiableList(pieces);
    }
}
