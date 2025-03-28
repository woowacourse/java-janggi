package janggiGame.piece;

import janggiGame.Position;
import janggiGame.piece.character.Dynasty;
import janggiGame.piece.character.PieceType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Elephant extends Piece {
    public Elephant(Dynasty dynasty) {
        super(dynasty);
    }

    private static boolean isFirstMoveHorizontal(int dx, int dy) {
        return Math.abs(dx) == 3 && Math.abs(dy) == 2;
    }

    @Override
    public List<Position> getIntermediatePoints(Position origin, Position destination) {
        List<Position> route = new ArrayList<>();

        int dx = origin.calculateRowChange(destination);
        int dy = origin.calculateColumnChange(destination);

        validateRoute(dx, dy);

        Function<Position, Position> firstMove = getFirstMove(dx, dy);
        origin = firstMove.apply(origin);
        route.add(origin);

        Function<Position, Position> diagonalMove = getDiagonalMove(dx, dy);
        route.add(diagonalMove.apply(origin));

        return route;
    }

    private void validateRoute(int dx, int dy) {
        if (dx == 0 && dy == 0) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로 이동할 수 없습니다.");
        }

        if (!(isFirstMoveVertical(dx, dy) || isFirstMoveHorizontal(dx, dy))) {
            throw new UnsupportedOperationException("[ERROR] 상이 이동할 수 있는 목적지가 아닙니다.");
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

    private Function<Position, Position> getDiagonalMove(int dx, int dy) {
        if (dx > 0 && dy > 0) {
            return Position::upRight;
        }

        if (dx > 0) {
            return Position::downRight;
        }

        if (dy > 0) {
            return Position::upLeft;
        }

        return Position::downLeft;
    }

    private boolean isFirstMoveVertical(int dx, int dy) {
        return Math.abs(dx) == 2 && Math.abs(dy) == 3;
    }

    @Override
    public void validateMove(Map<Position, Piece> IntermediatePointsWithPiece, Piece destinationPiece) {
        super.validateMove(IntermediatePointsWithPiece, destinationPiece);

        IntermediatePointsWithPiece.values()
                .stream()
                .filter(piece -> piece.getType() != PieceType.EMPTY)
                .findAny()
                .ifPresent(piece -> {
                    throw new UnsupportedOperationException("[ERROR] 상은 경로에 말이 존재하면 이동할 수 없습니다.");
                });

    }

    @Override
    public PieceType getType() {
        return PieceType.ELEPHANT;
    }
}
