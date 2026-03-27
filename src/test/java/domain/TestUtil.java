package domain;

import domain.position.Position;

public class TestUtil {

    public static Position createPosition(int row, int col) {
        return new Position(row, col);
    }
}
