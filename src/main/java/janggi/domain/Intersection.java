package janggi.domain;

import janggi.domain.location.Vector;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.List;

public class Intersection {

    private final List<Vector> vectors;
    private final boolean isPalace;
    private Piece piece;

    private Intersection(List<Vector> vectors, Piece piece, boolean isPalace) {
        this.vectors = vectors;
        this.piece = piece;
        this.isPalace = isPalace;
    }

    public static Intersection of(List<Vector> vectors, Piece piece, boolean isPalace) {
        return new Intersection(vectors, piece, isPalace);
    }

    public boolean isPalace() {
        return isPalace;
    }

    public boolean hasPiece(PieceType pieceType) {
        return piece.isSame(pieceType);
    }

    public void place(Piece piece) {
        this.piece = piece;
    }

    public void leave() {
        this.piece = EmptyPiece.getInstance();
    }

    public boolean isEmpty() {
        return piece.isEmpty();
    }

    public Piece getPiece() {
        return piece;
    }

    public List<Vector> getVectors() {
        return vectors;
    }
}
