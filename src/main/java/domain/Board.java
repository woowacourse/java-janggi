package domain;

import domain.piece.Empty;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class Board {
    private final Map<Point, Piece> locations;

    public Board() {
        Map<Point, Piece> locations = new HashMap<>();
        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 9; column++) {
                locations.put(new Point(row, column), new Empty());
            }
        }
        this.locations = locations;
    }

    public Map<Point, Piece> getLocations() {
        return locations;
    }
}
