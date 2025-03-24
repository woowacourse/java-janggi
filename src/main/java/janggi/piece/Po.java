package janggi.piece;

import janggi.point.Direction;
import janggi.point.Hurdles;
import janggi.point.InitialPoint;
import janggi.point.Point;
import janggi.game.Team;
import janggi.point.Route;
import java.util.ArrayList;
import java.util.List;

public class Po implements Movable {

    private static final String NAME = "포";
    private static final List<Po> pos;

    private final Team team;
    private final Point point;

    public Po(Team team, Point point) {
        this.team = team;
        this.point = point;
    }

    static {
        List<Po> createdPieces = new ArrayList<>();
        for (Point point : InitialPoint.PO.getRedPoints()) {
            createdPieces.add(new Po(Team.HAN, point));
        }
        for (Point point : InitialPoint.PO.getBluePoints()) {
            createdPieces.add(new Po(Team.CHO, point));
        }
        pos = createdPieces;
    }

    public static List<Po> values() {
        return new ArrayList<>(pos);
    }


    @Override
    public boolean canMove(Point targetPoint, Hurdles hurdles) {
        Direction direction = Direction.cardinalFrom(this.point, targetPoint);
        return isRouteHaveNoHurdle(targetPoint, hurdles, direction);
    }

    private boolean isRouteHaveNoHurdle(Point targetPoint, Hurdles hurdles, Direction direction) {
        Route route = Route.repeat(direction, this.point, targetPoint);
        return route.hasNoHurdle(this, targetPoint, hurdles);
    }

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Po(team, afterPoint);
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
}
