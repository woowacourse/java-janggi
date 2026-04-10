package domain.place.moveStrategy;


import domain.position.Position;

public enum Direction {
    TOP(1, 0),
    DOWN(-1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    LEFT_TOP(1, -1),
    RIGHT_TOP(1, 1),
    LEFT_DOWN(-1, -1),
    RIGHT_DOWN(-1, 1);

    private final int row;
    private final int column;

    Direction(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public static Direction findDirection(Position from, Position to) {
        int rowDiff = to.getRow() - from.getRow();
        int columnDiff = to.getColumn() - from.getColumn();

        for (Direction direction : values()) {
            if (direction.row == rowDiff && direction.column == columnDiff) {
                return direction;
            }
        }
        throw new IllegalArgumentException("해당 좌표들 사이의 방향이 없습니다.");
    }
}
