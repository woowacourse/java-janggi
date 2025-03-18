package janggi.piece;

import janggi.point.InitialPoint;
import janggi.Movable;
import janggi.point.Point;
import janggi.TeamColor;
import java.util.ArrayList;
import java.util.List;

public class Gung implements Movable {

    private static final String NAME = "궁";
    private static final List<Gung> gungs;

    private final TeamColor color;
    private final Point point;

    public Gung(TeamColor color, Point point) {
        this.color = color;
        this.point = point;
    }

    static {
        List<Gung> createdPieces = new ArrayList<>();
        for(int i=0; i<1; i++) {
            for (Point point : InitialPoint.GUNG.getRedPoints()) {
                createdPieces.add(new Gung(TeamColor.RED, point));
            }
            for (Point point : InitialPoint.GUNG.getBluePoints()) {
                createdPieces.add(new Gung(TeamColor.BLUE, point));
            }
        }
        gungs = createdPieces;
    }
    public static List<Gung> values() {
        return new ArrayList<>(gungs);
    }

    public boolean isMovable(Point targetPoint) {
        List<Point> candidates = List.of(
                point.move(0, 1),
                point.move(0, -1),
                point.move(1, 0),
                point.move(-1, 0)
        );
        return candidates.contains(targetPoint);
    }
}
