package janggiGame.piece;

import janggiGame.Position;
import janggiGame.piece.character.Dynasty;
import janggiGame.piece.character.PieceType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Horse extends Piece {
    public Horse(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public List<Position> getIntermediatePoints(Position origin, Position destination) {
        List<Position> route = new ArrayList<>();

        int dx = origin.calculateRowChange(destination);
        int dy = origin.calculateColumnChange(destination);

        validateRoute(dx, dy);

        Function<Position, Position> firstMove = getFirstMove(dx, dy);
        route.add(firstMove.apply(origin));

        return route;
    }

    private void validateRoute(int dx, int dy) {
        if (dx == 0 && dy == 0) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로 이동할 수 없습니다.");
        }

        if (!(isFirstMoveVertical(dx, dy) || isFirstMoveHorizontal(dx, dy))) {
            throw new UnsupportedOperationException("[ERROR] 마가 이동할 수 있는 목적지가 아닙니다.");
        }
    }

    private Function<Position, Position> getFirstMove(int dx, int dy) {
        if (isFirstMoveVertical(dx, dy)) {
            if (dy > 0) {
                return Position::up;
            }
            return Position::down;
        }
        if (dx > 0) {
            return Position::right;
        }
        return Position::left;
    }

    private boolean isFirstMoveVertical(int dx, int dy) {
        return Math.abs(dx) == 1 && Math.abs(dy) == 2;
    }

    private boolean isFirstMoveHorizontal(int dx, int dy) {
        return Math.abs(dx) == 2 && Math.abs(dy) == 1;
    }

    @Override
    public void validateMove(Map<Position, Piece> IntermediatePointsWithPiece, Piece destinationPiece) {
        super.validateMove(IntermediatePointsWithPiece, destinationPiece);

        IntermediatePointsWithPiece.values()
                .stream()
                .filter(piece -> piece.getType() != PieceType.EMPTY)
                .findAny()
                .ifPresent(piece -> {
                    throw new UnsupportedOperationException("[ERROR] 마는 경로에 말이 존재하면 이동할 수 없습니다.");
                });
    }

    @Override
    public PieceType getType() {
        return PieceType.HORSE;
    }
}
