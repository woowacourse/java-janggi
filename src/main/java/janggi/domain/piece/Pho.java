package janggi.domain.piece;

import static java.lang.Math.abs;

import janggi.domain.Point;
import janggi.domain.status.Team;
import java.util.ArrayList;
import java.util.List;

public class Pho implements Piece {

    private final Team team;
    private final PieceType type;

    public Pho(Team team) {
        this.team = team;
        this.type = PieceType.PHO;
    }

    @Override
    public boolean isSameTeam(Team team) {
        return this.team.equals(team);
    }

    @Override
    public List<Point> getRoute(Point from, Point to) {
        int pathX = to.calculatePathX(from);
        int pathY = to.calculatePathY(from);

        if (pathY != 0 && pathX != 0) {
            throw new IllegalArgumentException("[ERROR] 해당 기물의 이동 규칙에 어긋납니다.");
        }

        int signX = Integer.compare(pathX, 0);
        int signY = Integer.compare(pathY, 0);

        int distance = Math.max(abs(pathX), abs(pathY));

        List<Point> route = new ArrayList<>();

        for (int i = 1; i < distance; i++) {
            int nextX = from.getX() + (signX * i);
            int nextY = from.getY() + (signY * i);
            route.add(Point.of(nextX, nextY));
        }
        return route;
    }

    @Override
    public boolean canMove(List<Piece> route) {
        if (route.size() > 1) {
            return false;
        }
        return route.stream().anyMatch(piece -> !piece.isSameType(type));
    }

    @Override
    public boolean isSameType(PieceType type) {
        return this.type.equals(type);
    }

    @Override
    public boolean canCapture(Piece target) {
        return !target.isSameType(PieceType.PHO);
    }

    @Override
    public PieceType getType() {
        return type;
    }
}
