package janggi.fixture;

import janggi.board.position.Column;
import janggi.board.position.Position;
import janggi.board.position.Row;

public class PositionFixture {
    public static Position createPosition(int column, int row) {
        return new Position(Column.from(column), Row.from(row));
    }
}
