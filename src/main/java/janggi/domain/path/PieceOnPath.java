package janggi.domain.path;

import janggi.domain.piece.Piece;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class PieceOnPath implements Iterable<Piece> {

    private final List<Piece> pieces;

    public PieceOnPath() {
        this.pieces = new ArrayList<>();
    }

    public void add(Piece piece) {
        pieces.add(piece);
    }

    public Stream<Piece> stream() {
        return pieces.stream();
    }

    @Override
    public Iterator<Piece> iterator() {
        return pieces.iterator();
    }
}
