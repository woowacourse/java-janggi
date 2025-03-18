package domain.piece;

import domain.Coordinate;
import java.util.Objects;

public class Sa {

    private final Piece piece;

    public Sa(Piece piece) {
        this.piece = piece;
    }

    public Sa move(Coordinate coordinate) {
        return new Sa(piece.move(coordinate));
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
