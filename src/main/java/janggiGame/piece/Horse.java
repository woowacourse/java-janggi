package janggiGame.piece;

import janggiGame.board.Dot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class Horse extends Piece {
    private static final String NAME = "마";

    public Horse(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public List<Dot> getRoute(Dot origin, Dot destination) {
        List<Dot> route = new ArrayList<>();

        int dx = origin.getDx(destination);
        int dy = origin.getDy(destination);

        validateRoute(dx, dy);

        Function<Dot, Dot> firstMove = getFirstMove(dx, dy);
        route.add(firstMove.apply(origin));

        return route;
    }

    private Function<Dot, Dot> getFirstMove(int dx, int dy) {
        if (isFirstMoveVertical(dx, dy)) {
            if (dy > 0) {
                return Dot::up;
            }
            return Dot::down;
        }
        if (dx > 0) {
            return Dot::right;
        }
        return Dot::left;
    }


    @Override
    public void validateRoute(int dx, int dy) {
        if (!(isFirstMoveVertical(dx, dy) || isFirstMoveHorizontal(dx, dy))) {
            throw new UnsupportedOperationException("[ERROR] 마가 이동할 수 있는 목적지가 아닙니다.");
        }
    }

    private boolean isFirstMoveVertical(int dx, int dy) {
        return Math.abs(dx) == 1 && Math.abs(dy) == 2;
    }

    private boolean isFirstMoveHorizontal(int dx, int dy) {
        return Math.abs(dx) == 2 && Math.abs(dy) == 1;
    }

    @Override
    public void validateMove(Map<Dot, Piece> routesWithPiece, Piece destinationPiece) {
        validateSameDynasty(destinationPiece);

        routesWithPiece.values()
                .stream()
                .filter(Objects::nonNull)
                .findAny()
                .ifPresent(piece -> {
                    throw new UnsupportedOperationException("[ERROR] 마는 경로에 말이 존재하면 이동할 수 없습니다.");
                });
    }

    @Override
    public String getName() {
        return NAME;
    }
}
