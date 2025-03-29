package janggiGame.piece.straightMovePiece;

import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import janggiGame.piece.Type;
import janggiGame.position.Palace;
import janggiGame.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class StraightMovePiece extends Piece {
    public StraightMovePiece(Dynasty dynasty, Type type) {
        super(dynasty, type);
    }

    @Override
    public List<Position> getRoute(Position origin, Position destination) {
        List<Position> route = new ArrayList<>();

        int dx = origin.getDx(destination);
        int dy = origin.getDy(destination);

        if (Palace.isPalaceDiagonalMove(origin, destination)) {
            if (dy > 0) {
                route.addAll(getDirectionalRoute(origin, dx, Position::upRight, Position::upLeft));
                return route;
            }

            route.addAll(getDirectionalRoute(origin, dx, Position::downRight, Position::downLeft));
            return route;
        }

        validateRoute(dx, dy);

        if (dx == 0) {
            route.addAll(getDirectionalRoute(origin, dy, Position::up, Position::down));
            return route;
        }

        if (dy == 0) {
            route.addAll(getDirectionalRoute(origin, dx, Position::right, Position::left));
        }

        return route;
    }

    private List<Position> getDirectionalRoute(Position origin, int delta,
                                               Function<Position, Position> positiveMove,
                                               Function<Position, Position> negativeMove) {
        List<Position> route = new ArrayList<>();
        Function<Position, Position> moveFunction = getMoveFunction(delta, positiveMove, negativeMove);

        int steps = Math.abs(delta) - 1;

        for (int i = 0; i < steps; i++) {
            origin = moveFunction.apply(origin);
            route.add(origin);
        }

        return route;
    }

    private Function<Position, Position> getMoveFunction(int delta,
                                                         Function<Position, Position> positiveMove,
                                                         Function<Position, Position> negativeMove) {
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
