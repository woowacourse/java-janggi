package move;

import direction.Direction;
import direction.Point;
import java.util.ArrayList;
import java.util.List;
import piece.Pieces;

public class ChariotMovement implements MovementRule {

    @Override
    public void validateDestination(Point from, Point to) {
        validateStraightDestination(from, to);
        validateStartSameDestination(from, to);
    }

    @Override
    public void checkPaths(Pieces pieces, Point from, Point to) {
        findPaths(from, to).forEach(pieces::validateNotContainPiece);
    }

    private List<Point> findPaths(Point from, Point to) {
        Direction direction = Direction.find(from, to);
        List<Point> paths = new ArrayList<>();
        Point current = new Point(from.x(), from.y());
        current = current.apply(direction);
        while(!current.equals(to)) {
            paths.add(current);
            current = current.apply(direction);
        }
        return paths;
    }

    private void validateStraightDestination(Point from, Point to) {
        if(from.x() != to.x() && from.y() != to.y()) {
            throw new IllegalArgumentException();
        }
    }

    private void validateStartSameDestination(Point from, Point to) {
        if(from.equals(to)) {
            throw new IllegalArgumentException();
        }
    }
}
