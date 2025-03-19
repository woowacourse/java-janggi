package janggi.domain;

import janggi.domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;

public class Pieces {

    private final List<Piece> pieces;

    public Pieces(final List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }
}
