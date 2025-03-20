package janggiGame.piece;

import janggiGame.board.Dot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

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

        validateRoute(dx, dy);

        Function<Dot, Dot> firstMove = getFirstMove(dx, dy);
        origin = firstMove.apply(origin);
        route.add(origin);

        Function<Dot, Dot> diagonalMove = getDiagonalMove(dx, dy);
        route.add(diagonalMove.apply(origin));

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

    private Function<Dot, Dot> getDiagonalMove(int dx, int dy) {
        if (dx > 0 && dy > 0) {
            return Dot::upRight;
        }

        if (dx > 0) {
            return Dot::downRight;
        }

        if (dy > 0) {
            return Dot::upLeft;
        }

        return Dot::downLeft;
    }

    @Override
    public void validateRoute(int dx, int dy) {
        if (!(isFirstMoveVertical(dx, dy) || isFirstMoveHorizontal(dx, dy))) {
            throw new UnsupportedOperationException("[ERROR] 상이 이동할 수 있는 목적지가 아닙니다.");
        }
    }

    private boolean isFirstMoveVertical(int dx, int dy) {
        return Math.abs(dx) == 2 && Math.abs(dy) == 3;
    }

    @Override
    public void validateMove(Map<Dot, Piece> routesWithPiece, Piece destinationPiece) {
        validateSameDynasty(destinationPiece);

        routesWithPiece.values()
                .stream()
                .filter(Objects::nonNull)
                .findAny()
                .ifPresent(piece -> {
                    throw new UnsupportedOperationException("[ERROR] 상은 경로에 말이 존재하면 이동할 수 없습니다.");
                });

    }

    @Override
    public String getName() {
        return NAME;
    }
}
