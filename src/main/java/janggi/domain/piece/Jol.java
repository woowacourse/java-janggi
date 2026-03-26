package janggi.domain.piece;

import static java.lang.Math.abs;

import janggi.domain.Point;
import janggi.domain.status.Team;
import java.util.List;

public class Jol implements Piece {

    private static final int MAX_DISTANCE = 1;

    private final Team team;
    private final PieceType type;

    public Jol(Team team) {
        this.team = team;
        this.type = PieceType.JOL;
    }

    @Override
    public boolean isSameTeam(Team team) {
        return this.team.equals(team);
    }

    @Override
    public List<Point> getRoute(Point from, Point to) {
        int pathX = to.getX() - from.getX();
        int pathY = to.getY() - from.getY();

        int signY = Integer.compare(pathY, 0);
        int distanceX = abs(pathX);
        int distanceY = abs(pathY);

        if (distanceX > MAX_DISTANCE || distanceY > MAX_DISTANCE || (distanceX + distanceY > MAX_DISTANCE)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물의 이동 규칙에 어긋납니다.");
        }
        if ((team.equals(Team.CHO) && signY < 0) || (team.equals(Team.HAN) && signY > 0)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물의 이동 규칙에 어긋납니다.");
        }
        return List.of(to);
    }

    @Override
    public boolean canMove(List<Piece> route) {
        return route.stream()
                .noneMatch(piece -> piece.isSameTeam(team));
    }

    @Override
    public boolean isSameType(PieceType type) {
        return this.type.equals(type);
    }

    @Override
    public PieceType getType() {
        return type;
    }
}

