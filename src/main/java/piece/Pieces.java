package piece;

import java.util.Collections;
import java.util.List;

public class Pieces {

    private final List<Piece> pieces;

    public Pieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public int size() {
        return pieces.size();
    }

    public List<Piece> getPieces() {
        return Collections.unmodifiableList(pieces);
    }
}
