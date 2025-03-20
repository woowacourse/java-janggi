package janggiGame.piece;

import janggiGame.board.Dot;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Horse extends Piece {
    private static final String NAME = "마";

    public Horse(Dynasty dynasty) {
        super(dynasty);
    }

    private static boolean isFirstMoveHorizontal(int dx, int dy) {
        return Math.abs(dx) == 2 && Math.abs(dy) == 1;
    }

    @Override
    public List<Dot> getRoute(Dot origin, Dot destination) {
        List<Dot> route = new ArrayList<>();

        int dx = origin.getDx(destination);
        int dy = origin.getDy(destination);

        if (!(isFirstMoveVertical(dx, dy) || isFirstMoveHorizontal(dx, dy))) {
            throw new UnsupportedOperationException("[ERROR] 마가 이동할 수 있는 목적지가 아닙니다.");
        }

        if (dx == 0 && dy == 0) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로 이동할 수 없습니다.");
        }

        if (isFirstMoveVertical(dx, dy)) {
            if (dy > 0) {
                route.add(origin.up());
                return route;
            }

            route.add(origin.down());
            return route;
        }

        if (dx > 0) {
            route.add(origin.right());
            return route;
        }

        route.add(origin.left());
        return route;
    }

    private boolean isFirstMoveVertical(int dx, int dy) {
        return Math.abs(dx) == 1 && Math.abs(dy) == 2;
    }

    @Override
    public boolean canMove(Map<Dot, Piece> routesWithPiece, Piece destinationPiece) {
        if (isSameDynasty(destinationPiece)) {
            throw new UnsupportedOperationException("[ERROR] 같은 나라의 말은 공격할 수 없습니다.");
        }

        return routesWithPiece.values()
                .stream()
                .noneMatch(Objects::nonNull);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
