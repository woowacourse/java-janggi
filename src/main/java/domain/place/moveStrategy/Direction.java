package domain.place.moveStrategy;

import domain.position.Position;

public enum Direction {
    TOP(-1,0),
    DOWN(1,0),
    LEFT(0, -1),
    RIGHT(0, 1),
    LEFT_UP(1, -1),
    RIGHT_UP(1, 1),
    LEFT_DOWN(-1, -1),
    RIGHT_DOWN(-1, 1);

    private final int row;
    private final int column;

    Direction(int row, int column){
        this.row = row;
        this.column = column;
    }

    public int getRow(){
        return row;
    }

    public int getColumn(){
        return column;
    }

    static Direction getStraightDirection(Position from, Position to) {
        int dRow = to.getRow() - from.getRow();
        int dCol = to.getColumn() - from.getColumn();

        if (dRow == 0 && dCol < 0) return LEFT;
        if (dRow == 0 && dCol > 0) return RIGHT;
        if (dCol == 0 && dRow < 0) return TOP;
        if (dCol == 0 && dRow > 0) return DOWN;

        throw new IllegalArgumentException("직선 이동 아님");
    }
}
