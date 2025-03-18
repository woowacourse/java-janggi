package domain.piece;

import java.util.Objects;

public class Cha {

    private final Piece piece;

    public Cha(Piece piece) {
        this.piece = piece;
    }

    public Cha moveLeft(int distance) {
        Piece piece = this.piece.moveLeft(distance);
        return new Cha(piece);
    }

    public Cha moveRight(int distance) {
        Piece piece = this.piece.moveRight(distance);
        return new Cha(piece);
    }

    public Cha moveForward(int distance) {
        Piece piece = this.piece.moveForward(distance);
        return new Cha(piece);
    }

    public Cha moveBackward(int distance) {
        Piece piece = this.piece.moveBackward(distance);
        return new Cha(piece);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Cha cha = (Cha) object;
        return Objects.equals(piece, cha.piece);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(piece);
    }

    @Override
    public String toString() {
        return "Cha{" +
                "piece=" + piece +
                '}';
    }
}

