package domain;

import domain.piece.Empty;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class Board {
    private final Map<Point, Piece> locations;

    public Board() {
        Map<Point, Piece> locations = new HashMap<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 10; j++) {
                locations.put(new Point(i, j), new Empty());
            }
        }
        this.locations = locations;
    }

    public Board(Map<Point, Piece> locations) {
        this.locations = locations;
    }


    public Map<Point, Piece> getLocations() {
        return locations;
    }
}
