package domain;

import domain.pieces.Piece;
import execptions.JanggiArgumentException;
import java.util.HashMap;
import java.util.Map;

public final class Board {

    private static final int VALID_SIZE = 90;

    private final Map<Point, Piece> locations;

    public Board(Map<Point, Piece> locations) {
        validate(locations);
        this.locations = locations;
    }

    private void validate(Map<Point, Piece> locations) {
        if (locations.size() != VALID_SIZE) {
            throw new JanggiArgumentException("보드의 크기는 9x10 이어야 합니다.");
        }
    }

    public Map<Point, Piece> getLocations() {
        return new HashMap<>(locations);
    }
}
