package domain.piece;

import java.util.Objects;

public class Sa {

    private final Piece piece;

    public Sa(Piece piece) {
        this.piece = piece;
    }

    public Sa moveLeft() {
        Piece piece = this.piece.moveLeft(1);
        return new Sa(piece);
    }

    public Sa moveRight() {
        Piece piece = this.piece.moveRight(1);
        return new Sa(piece);
    }

    public Sa moveForward() {
        Piece piece = this.piece.moveForward(1);
        return new Sa(piece);
    }

    public Sa moveBackward() {
        Piece piece = this.piece.moveBackward(1);
        return new Sa(piece);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Sa sa = (Sa) object;
        return Objects.equals(piece, sa.piece);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(piece);
    }

    @Override
    public String toString() {
        return "Sa{" +
                "piece=" + piece +
                '}';
    }
}
