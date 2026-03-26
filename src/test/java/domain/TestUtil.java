package domain;

import domain.position.Position;

public class TestUtil {

    public static Position createPosition(int x, int y) {
        return new Position(x, y);
    }
}
