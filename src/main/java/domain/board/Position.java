package domain.board;

import domain.piece.strategy.Delta;

import java.util.ArrayList;
import java.util.List;

public record Position(int x, int y) {

    private static final int MIN_X = 1;
    private static final int MAX_X = 9;
    private static final int MIN_Y = 1;
    private static final int MAX_Y = 10;
    private static final String INVALID_X_COORDINATE_ERROR_MESSAGE = "[ERROR] x 좌표가 올바르지 않습니다.";
    private static final String INVALID_Y_COORDINATE_ERROR_MESSAGE = "[ERROR] y 좌표가 올바르지 않습니다.";
    private static final String INVALID_DESTINATION_POSITION_ERROR_MESSAGE = "[ERROR] 해당 좌표로 이동할 수 없습니다.";

    public Position {
        if (x < MIN_X || x > MAX_X) {
            throw new IllegalArgumentException(INVALID_X_COORDINATE_ERROR_MESSAGE);
        }

        if (y < MIN_Y || y > MAX_Y) {
            throw new IllegalArgumentException(INVALID_Y_COORDINATE_ERROR_MESSAGE);
        }
    }

    public int calculateDx(Position to) {
        return to.x - this.x;
    }

    public int calculateDy(Position to) {
        return to.y - this.y;
    }

    public Delta calculateDelta(Position to) {
        return new Delta(to.x - this.x, to.y - this.y);
    }

    public Position move(Delta delta) {
        return new Position(this.x + delta.dx(), this.y + delta.dy());
    }

    public List<Position> findPath(Position destination) {
        List<Position> path = new ArrayList<>();

        int dx = Integer.compare(destination.x(), this.x());
        int dy = Integer.compare(destination.y(), this.y());

        if (dx != 0 && dy != 0) {
            throw new IllegalArgumentException(INVALID_DESTINATION_POSITION_ERROR_MESSAGE);
        }

        int currentX = this.x() + dx;
        int currentY = this.y() + dy;

        while (currentX != destination.x() || currentY != destination.y()) {
            path.add(new Position(currentX, currentY));

            currentX += dx;
            currentY += dy;
        }

        return path;
    }
}

