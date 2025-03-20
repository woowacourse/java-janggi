package janggiGame.piece;

import janggiGame.board.Dot;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Elephant extends Piece {
    private static final String NAME = "상";

    public Elephant(Dynasty dynasty) {
        super(dynasty);
    }

    private static boolean isFirstMoveHorizontal(int dx, int dy) {
        return Math.abs(dx) == 3 && Math.abs(dy) == 2;
    }

    @Override
    public List<Dot> getRoute(Dot origin, Dot destination) {
        List<Dot> route = new ArrayList<>();

        int dx = origin.getDx(destination);
        int dy = origin.getDy(destination);

        if (!(isFirstMoveVertical(dx, dy) || isFirstMoveHorizontal(dx, dy))) {
            throw new UnsupportedOperationException("[ERROR] 상이 이동할 수 있는 목적지가 아닙니다.");
        }

        if (dx == 0 && dy == 0) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로 이동할 수 없습니다.");
        }

        if (isFirstMoveVertical(dx, dy)) {
            if (dy > 0) {
                origin = origin.up();
                route.add(origin);

                if (dx > 0) {
                    route.add(origin.upRight());
                    return route;
                }

                route.add(origin.upLeft());
                return route;
            }

            origin = origin.down();
            route.add(origin);

            if (dx > 0) {
                route.add(origin.downRight());
                return route;
            }

            route.add(origin.downLeft());
            return route;
        }

        if (dx > 0) {
            origin = origin.right();
            route.add(origin);

            if (dy > 0) {
                route.add(origin.upRight());
                return route;
            }

            route.add(origin.downRight());
            return route;
        }

        origin = origin.left();
        route.add(origin);

        if (dy > 0) {
            route.add(origin.upLeft());
            return route;
        }

        route.add(origin.downLeft());
        return route;
    }

    private boolean isFirstMoveVertical(int dx, int dy) {
        return Math.abs(dx) == 2 && Math.abs(dy) == 3;
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
