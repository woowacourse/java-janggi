package piece;

import direction.Direction;
import direction.Point;
import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    private final String name; // enum -> outputFormatter
    protected Point currentPosition;

    public Piece(String name, Point currentPosition) {
        this.name = name;
        this.currentPosition = currentPosition;
    }

    protected Piece(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isEqualPositionWith(Point targetPoint) {
        return currentPosition.equals(targetPoint);
    }

    public abstract void validateDestination(Point to);

    public abstract void checkPaths(Pieces allPieces, Point to);

    public void move(Point to) {
        currentPosition = to;
    }

    protected List<Point> findStraightPaths(Point from, Point to) {
        Direction direction = Direction.find(from, to);
        List<Point> paths = new ArrayList<>();
        Point current = new Point(from.x(), from.y());
        current = current.apply(direction);
        while (!current.equals(to)) {
            paths.add(current);
            current = current.apply(direction);
        }
        return paths;
    }

    protected void validateStraightDestination(Point from, Point to) {
        if (from.x() != to.x() && from.y() != to.y()) {
            throw new IllegalArgumentException("[ERROR] 직선 이동만 가능합니다.");
        }
    }

    protected void validateNotSamePosition(Point from, Point to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("[ERROR] 출발지와 목적지는 달라야 합니다.");
        }
    }

    protected boolean isSameType(String otherName) {
        return name.equalsIgnoreCase(otherName);
    }
}
