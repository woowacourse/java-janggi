package janggi.piece;

import janggi.point.InitialPoint;
import janggi.Movable;
import janggi.point.Point;
import janggi.TeamColor;
import java.util.ArrayList;
import java.util.List;

public class Ma implements Movable {

    private static final String NAME = "마";
    private static final List<Ma> mas;

    private final TeamColor color;
    private final Point point;

    public Ma(TeamColor color, Point point) {
        this.color = color;
        this.point = point;
    }

    static {
        List<Ma> createdPieces = new ArrayList<>();
        for (Point point : InitialPoint.MA.getRedPoints()) {
            createdPieces.add(new Ma(TeamColor.RED, point));
        }
        for (Point point : InitialPoint.MA.getBluePoints()) {
            createdPieces.add(new Ma(TeamColor.BLUE, point));
        }
        mas = createdPieces;
    }
    public static List<Ma> values() {
        return new ArrayList<>(mas);
    }

    public boolean isMovable(Point targetPoint) {
        List<Point> candidates = List.of(
                point.move(-2, 1),
                point.move(-1, 2),
                point.move(1, 2),
                point.move(2, 1),
                point.move(2, -1),
                point.move(1, -2),
                point.move(-1, -2),
                point.move(-2, -1)
        );

        return candidates.contains(targetPoint);
    }

    @Override
    public Point getPoint() {
        return point;
    }
}
