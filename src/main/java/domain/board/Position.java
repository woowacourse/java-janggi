package domain.board;

import java.util.ArrayList;
import java.util.List;

public record Position(int x, int y) {

    private static final int MIN_X = 1;
    private static final int MAX_X = 9;
    private static final int MIN_Y = 1;
    private static final int MAX_Y = 10;

    public Position {
        if (x < MIN_X || x > MAX_X) {
            throw new IllegalArgumentException("[ERROR] x 좌표가 올바르지 않습니다.");
        }

        if (y < MIN_Y || y > MAX_Y) {
            throw new IllegalArgumentException("[ERROR] y 좌표가 올바르지 않습니다.");
        }
    }

    public int calculateDx(Position to) {
        return to.x - this.x;
    }

    public int calculateDy(Position to) {
        return to.y - this.y;
    }

    List<Position> findOrthogonalPath(Position destination) {
        List<Position> path = new ArrayList<>();

        int dx = Integer.compare(destination.x(), this.x());
        int dy = Integer.compare(destination.y(), this.y());

        if (dx != 0 && dy != 0) {
            throw new IllegalArgumentException("[ERROR] 해당 좌표로 이동할 수 없습니다.");
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

    boolean isOrthogonallyAligned(Position to) {
        return this.x() == to.x() || this.y() == to.y();
    }
}
