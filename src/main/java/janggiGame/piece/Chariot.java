package janggiGame.piece;

import janggiGame.board.Dot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class Chariot extends Piece {
    private static final String NAME = "차";

    public Chariot(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public List<Dot> getRoute(Dot origin, Dot destination) {
        List<Dot> route = new ArrayList<>();

        int dx = origin.getDx(destination);
        int dy = origin.getDy(destination);

        validateRoute(dx, dy);

        if (dx == 0) {
            route.addAll(getDirectionalRoute(origin, dy, Dot::up, Dot::down));
            return route;
        }

        if (dy == 0) {
            route.addAll(getDirectionalRoute(origin, dx, Dot::right, Dot::left));
        }

        return route;
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
            throw new UnsupportedOperationException("[ERROR] 차가 이동할 수 있는 목적지가 아닙니다.");
        }

        if (dx == 0 && dy == 0) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로 이동할 수 없습니다.");
        }
    }

    @Override
    public void validateMove(Map<Dot, Piece> routesWithPiece, Piece destinationPiece) {
        validateSameDynasty(destinationPiece);

        routesWithPiece.values()
                .stream()
                .filter(Objects::nonNull)
                .findAny()
                .ifPresent(piece -> {
                    throw new UnsupportedOperationException("[ERROR] 차는 경로에 말이 존재하면 이동할 수 없습니다.");
                });
    }

    @Override
    public String getName() {
        return NAME;
    }
}
