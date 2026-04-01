package domain;

import domain.position.Position;

public class TestUtil {

    public static Position createPosition(int row, int column) {
        return new Position(row, column);
    }
}
