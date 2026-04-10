package janggi.domain.board;


import janggi.domain.common.Position;
import java.util.ArrayList;
import java.util.List;

public enum Palace {

    CHO(8, 10),
    HAN(1, 3);

    private final int START_X = 4;
    private final int END_X = 6;

    private final List<Position> palacePositions;

    Palace(int startY, int endY) {
        this.palacePositions = initializePalacePositions(startY, endY);
    }

    public boolean isInPalace(Position position) {
        return palacePositions.contains(position);
    }

    private List<Position> initializePalacePositions(int startY, int endY) {
        List<Position> palacePositions = new ArrayList<>();
        for (int x = START_X; x <= END_X; x++) {
            makePalacePositionsByYRange(startY, endY, palacePositions, x);
        }
        return palacePositions;
    }

    private void makePalacePositionsByYRange(int startY, int endY, List<Position> palacePositions, int x) {
        for (int y = startY; y <= endY; y++) {
            palacePositions.add(new Position(x, y));
        }
    }
}
