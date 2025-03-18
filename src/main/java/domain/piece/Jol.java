package domain.piece;

import java.util.Objects;

public class Jol {

    private final Piece piece;

    public Jol(Piece piece) {
        this.piece = piece;
    }

    public Jol moveLeft() {
        Piece piece = this.piece.moveLeft(1);
        return new Jol(piece);
    }

    public Jol moveRight() {
        Piece piece = this.piece.moveRight(1);
        return new Jol(piece);
    }

    public Jol moveForward() {
        Piece piece = this.piece.moveForward(1);
        return new Jol(piece);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Jol jol = (Jol) object;
        return Objects.equals(piece, jol.piece);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(piece);
    }

    @Override
    public String toString() {
        return "Jol{" +
                "piece=" + piece +
                '}';
    }
}
