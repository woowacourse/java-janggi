package janggi.piece;

import janggi.point.InitialPoint;
import janggi.Movable;
import janggi.point.Point;
import janggi.Team;
import java.util.ArrayList;
import java.util.List;

public class Gung implements Movable {

    private static final String NAME = "궁";
    private static final List<Gung> gungs;

    private final Team team;
    private final Point point;

    public Gung(Team team, Point point) {
        this.team = team;
        this.point = point;
    }

    static {
        List<Gung> createdPieces = new ArrayList<>();
        for (Point point : InitialPoint.GUNG.getRedPoints()) {
            createdPieces.add(new Gung(Team.HAN, point));
        }
        for (Point point : InitialPoint.GUNG.getBluePoints()) {
            createdPieces.add(new Gung(Team.CHO, point));
        }
        gungs = createdPieces;
    }
    public static List<Gung> values() {
        return new ArrayList<>(gungs);
    }

    @Override
    public boolean isMovable(Point targetPoint) {
        List<Point> candidates = new ArrayList<>();
        addPointsInRange(candidates, 0, 1);
        addPointsInRange(candidates, 0, -1);
        addPointsInRange(candidates, 1, 0);
        addPointsInRange(candidates, -1, 0);
        return candidates.contains(targetPoint);
    }

    private void addPointsInRange(List<Point> candidates, int rowMovingDistance, int columnMovingDistance) {
        try {
            candidates.add(point.move(rowMovingDistance, columnMovingDistance));
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Point getPoint() {
        return point;
    }

    @Override
    public List<Point> findRoute(Point targetPoint) {
        return List.of(targetPoint);
    }

    @Override
    public Team getTeam() {
        return this.team;
    }

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Gung(team, afterPoint);
    }
}
