package janggi.fixture;

import janggi.board.Position;

public class PositionFixture {
    public static Position createPosition(int column, int row) {
        return new Position(column, row);
    }
}
