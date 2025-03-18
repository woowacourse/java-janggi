package domain.piece;

import domain.Coordinate;
import java.util.Objects;

public class Goong {

    private final Piece piece;

    public Goong(Piece piece) {
        this.piece = piece;
    }

    public Goong move(Coordinate coordinate) {
        return new Goong(piece.move(coordinate));
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
