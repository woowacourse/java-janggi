package janggi.domain;

import janggi.domain.movestep.MoveStep;

public record Coordinate(int x, int y) {

    public Coordinate {
        validateXCoordinate(x);
        validateYCoordinate(y);
    }

    public boolean canMove(MoveStep moveStep) {
        int newX = this.x + moveStep.deltaX();
        int newY = this.y + moveStep.deltaY();
        return !isInvalidX(newX) && !isInvalidY(newY);
    }

    public Coordinate move(MoveStep moveStep) {
        int newX = this.x + moveStep.deltaX();
        int newY = this.y + moveStep.deltaY();

        return new Coordinate(newX, newY);
    }

    private boolean isInvalidX(int x) {
        return x < 1 || x > 9;
    }

    private void validateXCoordinate(int x) {
        if (isInvalidX(x)) {
            throw new IllegalArgumentException("가로 좌표는 1에서 9사이여야 합니다.");
        }
    }

    private boolean isInvalidY(int y) {
        return y < 1 || y > 10;
    }

    private void validateYCoordinate(int y) {
        if (isInvalidY(y)) {
            throw new IllegalArgumentException("세로 좌표는 1에서 10사이여야 합니다.");
        }
    }
}
