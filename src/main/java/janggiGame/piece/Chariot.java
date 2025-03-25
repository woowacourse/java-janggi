package janggiGame.piece;

import janggiGame.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Chariot extends Piece {
    public Chariot(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public List<Position> getIntermediatePoints(Position origin, Position destination) {
        int dx = origin.calculateRowChange(destination);
        int dy = origin.calculateColumnChange(destination);

        validateRoute(dx, dy);

        if (dx == 0) {
            return getDirectionalRoute(origin, dy, Position::up, Position::down);
        }

        return getDirectionalRoute(origin, dx, Position::right, Position::left);
    }

    private void validateRoute(int dx, int dy) {
        if (dx != 0 && dy != 0) {
            throw new UnsupportedOperationException("[ERROR] 차가 이동할 수 있는 목적지가 아닙니다.");
        }

        if (dx == 0 && dy == 0) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로 이동할 수 없습니다.");
        }
    }

    private List<Position> getDirectionalRoute(Position origin, int delta,
                                               Function<Position, Position> positiveMove,
                                               Function<Position, Position> negativeMove) {
        List<Position> route = new ArrayList<>();
        Function<Position, Position> moveFunction = getMoveFunction(delta, positiveMove, negativeMove);

        while (Math.abs(delta) > 1) {
            origin = moveFunction.apply(origin);
            route.add(origin);
            delta -= Integer.signum(delta);
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
    public void validateMove(Map<Position, Piece> IntermediatePointsWithPiece, Piece destinationPiece) {
        super.validateMove(IntermediatePointsWithPiece, destinationPiece);

        IntermediatePointsWithPiece.values()
                .stream()
                .filter(piece -> piece.getType() != PieceType.EMPTY)
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
