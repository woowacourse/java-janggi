package janggiGame.piece.straightMovePiece;

import janggiGame.Dot;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import janggiGame.piece.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class StraightMovePiece extends Piece {
    public StraightMovePiece(Dynasty dynasty, Type type) {
        super(dynasty, type);
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

        int steps = Math.abs(delta) - 1;

        for(int i = 0; i < steps; i++) {
            origin = moveFunction.apply(origin);
            route.add(origin);
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
    protected void validateRoute(int dx, int dy) {
        if (dx != 0 && dy != 0) {
            throw new UnsupportedOperationException("[ERROR] 이동할 수 있는 목적지가 아닙니다.");
        }

        if (dx == 0 && dy == 0) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로 이동할 수 없습니다.");
        }
    }
}
