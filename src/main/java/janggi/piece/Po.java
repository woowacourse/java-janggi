package janggi.piece;

import janggi.game.Board;
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
    public boolean isInMovingRange(Point targetPoint, Hurdles hurdles) {
        Direction direction = Direction.cardinalFrom(this.point, targetPoint);
        Route route = Route.repeat(direction, this.point, targetPoint);

        List<Point> crashPoints = route.findCrashes(hurdles);

        //bridge만 있어야 함
        if (crashPoints.size() == 1 || crashPoints.size() == 2) {
            if (bridgeNotExists(targetPoint, hurdles, crashPoints)) {
                return false;
            }
            //bridge + prey가 있어야 함
            if (crashPoints.size() == 2) {
                if (preyNotExists(targetPoint, hurdles, crashPoints)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private static boolean bridgeNotExists(Point targetPoint, Hurdles hurdles, List<Point> crashPoints) {
        Point bridgePoint = crashPoints.getFirst();
        if (hurdles.findByPoint(bridgePoint) instanceof Po) {
            return true;
        }
        if (bridgePoint.equals(targetPoint)) {
            return true;
        }
        return false;
    }

    private static boolean preyNotExists(Point targetPoint, Hurdles hurdles, List<Point> crashPoints) {
        Point preyPoint = crashPoints.getLast();
        Movable prey = hurdles.findByPoint(preyPoint);
        if (prey instanceof Po) {
            return true;
        }
        if (!preyPoint.equals(targetPoint)) {
            return true;
        }
        return false;
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
