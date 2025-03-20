package domain;

import domain.pieces.EmptyPiece;
import domain.pieces.Piece;
import java.util.HashMap;
import java.util.Map;

public final class Board {
    private final Map<Point, Piece> locations;

    public Board() {
        final Map<Point, Piece> locations = new HashMap<>();
        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 9; column++) {
                locations.put(new Point(row, column), new EmptyPiece());
            }
        }
        this.locations = locations;
    }

    public Map<Point, Piece> getLocations() {
        return locations;
    }
}
