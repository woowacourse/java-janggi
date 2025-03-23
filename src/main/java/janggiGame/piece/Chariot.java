package janggiGame.piece;

import janggiGame.board.Dot;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class Chariot extends Piece {
    public Chariot(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public List<Dot> getIntermediatePoints(Dot origin, Dot destination) {
        int dx = origin.calculateRowChange(destination);
        int dy = origin.calculateColumnChange(destination);

        validateRoute(dx, dy);

        if (dx == 0) {
            return getDirectionalRoute(origin, dy, Dot::up, Dot::down);
        }

        return getDirectionalRoute(origin, dx, Dot::right, Dot::left);
    }

    private void validateRoute(int dx, int dy) {
        if (dx != 0 && dy != 0) {
            throw new UnsupportedOperationException("[ERROR] 차가 이동할 수 있는 목적지가 아닙니다.");
        }

        if (dx == 0 && dy == 0) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로 이동할 수 없습니다.");
        }
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
    public void validateMove(Map<Dot, Piece> IntermediatePointsWithPiece, Piece destinationPiece) {
        super.validateMove(IntermediatePointsWithPiece, destinationPiece);

        IntermediatePointsWithPiece.values()
                .stream()
                .filter(Objects::nonNull)
                .findAny()
                .ifPresent(piece -> {
                    throw new UnsupportedOperationException("[ERROR] 차는 경로에 말이 존재하면 이동할 수 없습니다.");
                });
    }

    @Override
    public PieceType getType() {
        return PieceType.CHARIOT;
    }
}
