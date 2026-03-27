package janggi.domain.piece;

import static java.lang.Math.abs;

import janggi.domain.Point;
import janggi.domain.status.Team;
import java.util.List;

public class Jang implements Piece {

    private static final int MAX_DISTANCE = 1;

    private final Team team;
    private final PieceType type;

    public Jang(Team team) {
        this.team = team;
        this.type = PieceType.JANG;
    }

    @Override
    public boolean isSameTeam(Team team) {
        return this.team.equals(team);
    }

    @Override
    public List<Point> getRoute(Point from, Point to) {
        int pathX = to.calculatePathX(from);
        int pathY = to.calculatePathY(from);
        int distanceX = abs(pathX);
        int distanceY = abs(pathY);

        if (distanceX > MAX_DISTANCE || distanceY > MAX_DISTANCE || (distanceX == 0 && distanceY == 0)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물의 이동 규칙에 어긋납니다.");
        }
        return List.of(to);
    }

    @Override
    public boolean canMove(List<Piece> route) {
        return route.stream().noneMatch(piece -> piece.isSameTeam(team));
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
