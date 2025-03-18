package domain.piece;

import domain.Coordinate;
import java.util.Objects;

public class Jol {

    private final Piece piece;

    public Jol(Piece piece) {
        this.piece = piece;
    }

    public Jol move(Coordinate coordinate) {
        return new Jol(piece.move(coordinate));
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
