package domain.piece;

import domain.Coordinate;
import java.util.Objects;

public class Cha {

    private final Piece piece;

    public Cha(Piece piece) {
        this.piece = piece;
    }

    public Cha move(Coordinate coordinate) {
        return new Cha(piece.move(coordinate));
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

