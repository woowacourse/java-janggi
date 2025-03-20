package janggiGame.piece;

import janggiGame.board.Dot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class Cannon extends Piece {
    private static final String NAME = "포";

    public Cannon(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public List<Dot> getRoute(Dot origin, Dot destination) {
        int dx = origin.getDx(destination);
        int dy = origin.getDy(destination);

        validateRoute(dx, dy);

        if (dx == 0) {
            return getDirectionalRoute(origin, dy, Dot::up, Dot::down);
        }

        return getDirectionalRoute(origin, dx, Dot::right, Dot::left);
    }

    private List<Dot> getDirectionalRoute(Dot origin, int delta,
                                          Function<Dot, Dot> positiveMove,
                                          Function<Dot, Dot> negativeMove) {
        List<Dot> route = new ArrayList<>();
        Function<Dot, Dot> moveFunction = getMoveFunction(delta, positiveMove, negativeMove);

        while (Math.abs(delta) > 1) {
            origin = moveFunction.apply(origin);
            route.add(origin);
            delta -= Integer.signum(delta);
        }

        return route;
    }

    private Function<Dot, Dot> getMoveFunction(int delta,
                                               Function<Dot, Dot> positiveMove,
                                               Function<Dot, Dot> negativeMove) {
        if (delta > 0) {
            return positiveMove;
        }
        if (delta < 0) {
            return negativeMove;
        }
        return Function.identity();
    }

    @Override
    public void validateRoute(int dx, int dy) {
        if (dx != 0 && dy != 0) {
            throw new UnsupportedOperationException("[ERROR] 포가 이동할 수 있는 목적지가 아닙니다.");
        }

        if (dx == 0 && dy == 0) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로 이동할 수 없습니다.");
        }
    }

    @Override
    public void validateMove(Map<Dot, Piece> routesWithPiece, Piece destinationPiece) {
        validateSameDynasty(destinationPiece);

        if (destinationPiece != null && destinationPiece.getName().equals(NAME)) {
            throw new UnsupportedOperationException("[ERROR] 포는 포를 공격할 수 없습니다.");
        }

        List<Piece> pieces = routesWithPiece.values()
                .stream()
                .filter(Objects::nonNull)
                .toList();

        if (pieces.size() != 1) {
            throw new UnsupportedOperationException("[ERROR] 포는 경로에 단 한개의 기물만 존재해야 합니다.");
        }

        if (pieces.getFirst().getName().equals(NAME)) {
            throw new UnsupportedOperationException("[ERROR] 포끼리 뛰어 넘을 수 없습니다.");
        }
    }


    @Override
    public String getName() {
        return NAME;
    }
}
