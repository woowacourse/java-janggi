package janggi.piece;

import janggi.point.InitialPoint;
import janggi.Movable;
import janggi.point.Point;
import janggi.TeamColor;
import java.util.ArrayList;
import java.util.List;

public class Cha implements Movable {

    private static final String NAME = "차";
    private static final List<Cha> chas;

    private final TeamColor color;
    private final Point point;

    public Cha(TeamColor color, Point point) {
        this.color = color;
        this.point = point;
    }

    static {
        List<Cha> createdPieces = new ArrayList<>();
        for (Point point : InitialPoint.CHA.getRedPoints()) {
            createdPieces.add(new Cha(TeamColor.RED, point));
        }
        for (Point point : InitialPoint.CHA.getBluePoints()) {
            createdPieces.add(new Cha(TeamColor.BLUE, point));
        }
        chas = createdPieces;
    }
    public static List<Cha> values() {
        return new ArrayList<>(chas);
    }

    public boolean isMovable(Point targetPoint) {
        return point.isSameRow(targetPoint) || point.isSameColumn(targetPoint);
    }

    @Override
    public Point getPoint() {
        return point;
    }
}
