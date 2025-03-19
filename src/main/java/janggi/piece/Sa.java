package janggi.piece;

import janggi.point.InitialPoint;
import janggi.Movable;
import janggi.point.Point;
import janggi.Team;
import java.util.ArrayList;
import java.util.List;

public class Sa implements Movable {

    private static final String NAME = "사";
    private static final List<Sa> sas;

    private final Team team;
    private final Point point;

    public Sa(Team team, Point point) {
        this.team = team;
        this.point = point;
    }

    static {
        List<Sa> createdPieces = new ArrayList<>();
        for (Point point : InitialPoint.SA.getRedPoints()) {
            createdPieces.add(new Sa(Team.HAN, point));
        }
        for (Point point : InitialPoint.SA.getBluePoints()) {
            createdPieces.add(new Sa(Team.CHO, point));
        }
        sas = createdPieces;
    }
    public static List<Sa> values() {
        return new ArrayList<>(sas);
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
    public List<Point> findRoute(Point targetPoint) {
        return List.of(targetPoint);
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
    public Team getTeam() {
        return this.team;
    }

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Sang(team, afterPoint);
    }
}
