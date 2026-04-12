package janggi.domain.board;

import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.List;

public class Intersection {

    private final Location location;
    private final List<Vector> vectors;
    private final boolean isPalace;

    private Piece piece;

    private Intersection(Location location, List<Vector> vectors, Piece piece, boolean isPalace) {
        this.location = location;
        this.vectors = vectors;
        this.piece = piece;
        this.isPalace = isPalace;
    }

    public static Intersection of(Location location, List<Vector> vectors, Piece piece, boolean isPalace) {
        return new Intersection(location, vectors, piece, isPalace);
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

    public List<Location> calculateRoute(Intersection destination) {
        return piece.calculateRoute(this, destination);
    }

    public boolean isNotEmpty() {
        return piece.isNotEmpty();
    }

    public boolean isEmpty() {
        return piece.isEmpty();
    }

    public Location getLocation() {
        return location;
    }

    public List<Vector> getVectors() {
        return vectors;
    }

    public boolean isPalace() {
        return isPalace;
    }

    public Piece getPiece() {
        return piece;
    }
}
