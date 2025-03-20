package domain;

import domain.pieces.Piece;
import java.util.HashMap;
import java.util.Map;

public final class Board {

    private final Map<Point, Piece> locations;

    public Board(Map<Point, Piece> locations) {
        this.locations = locations;
    }

    public Map<Point, Piece> getLocations() {
        return new HashMap<>(locations);
    }
}
