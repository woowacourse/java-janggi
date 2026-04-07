package janggi.domain.piece;

import static java.lang.Math.abs;

import janggi.domain.Point;
import janggi.domain.status.Team;
import java.util.List;

public class Jang extends BasePiece {

    private static final int MAX_DISTANCE = 1;

    public Jang(Team team) {
        super(team, PieceType.JANG);
    }

    @Override
    public List<Point> getRoute(Point from, Point to) {
        if (!from.isInSamePalace(to)) {
            throw new IllegalArgumentException("[ERROR] 장은 같은 궁성 안에서만 이동할 수 있습니다.");
        }
        if (isNormalMove(from, to) || isPalaceDiagonalMove(from, to)) {
            return List.of();
        }
        throw new IllegalArgumentException("[ERROR] 장은 궁성 안에서 한 칸만 이동할 수 있습니다.");
    }

    private boolean isNormalMove(Point from, Point to) {
        int pathX = abs(to.getPathX(from));
        int pathY = abs(to.getPathY(from));
        return pathX + pathY == MAX_DISTANCE;
    }

    private boolean isPalaceDiagonalMove(Point from, Point to) {
        int pathX = abs(to.getPathX(from));
        int pathY = abs(to.getPathY(from));
        return pathX == 1 && pathY == 1 && from.isPalaceDiagonalMove(to);
    }
}
