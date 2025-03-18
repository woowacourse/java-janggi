package domain.piece;

import java.util.Objects;

public class Goong {

    private final Piece piece;

    public Goong(Piece piece) {
        this.piece = piece;
    }

    public Goong moveLeft() {
        Piece piece = this.piece.moveLeft(1);
        return new Goong(piece);
    }

    public Goong moveRight() {
        Piece piece = this.piece.moveRight(1);
        return new Goong(piece);
    }

    public Goong moveForward() {
        Piece piece = this.piece.moveForward(1);
        return new Goong(piece);
    }

    public Goong moveBackward() {
        Piece piece = this.piece.moveBackward(1);
        return new Goong(piece);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Goong sa = (Goong) object;
        return Objects.equals(piece, sa.piece);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(piece);
    }

    @Override
    public String toString() {
        return "Goong{" +
                "piece=" + piece +
                '}';
    }
}
