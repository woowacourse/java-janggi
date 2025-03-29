package janggiGame.piece;

import janggiGame.Position;
import janggiGame.piece.character.Dynasty;
import janggiGame.piece.character.PieceType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Cannon extends Piece {
    public Cannon(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public List<Position> getIntermediatePoints(Position origin, Position destination) {
        int dx = origin.calculateRowChange(destination);
        int dy = origin.calculateColumnChange(destination);

        if (getRouteInPalace(origin, destination) != null) {
            return getRouteInPalace(origin, destination);
        }
        validateRoute(dx, dy);

        if (dx == 0) {
            return getDirectionalRoute(origin, dy, Position::up, Position::down);
        }

        return getDirectionalRoute(origin, dx, Position::right, Position::left);
    }

    private void validateRoute(int dx, int dy) {
        if (dx != 0 && dy != 0) {
            throw new UnsupportedOperationException("[ERROR] 포가 이동할 수 있는 목적지가 아닙니다.");
        }
    }

    private List<Position> getRouteInPalace(Position origin, Position destination) {
        if (origin.equals(destination)) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로 이동할 수 없습니다.");
        }

        if (origin.isInChoPalaceDiagonal() && destination.isInChoPalaceDiagonal()) {
            if (origin.isCenterOfChoPalace() || destination.isCenterOfChoPalace()) {
                return List.of();
            }
            if (origin.getRow() + destination.getRow() == 8 &&
                    origin.getColumn() + destination.getColumn() == 2) {
                return List.of(Position.of(4, 1));
            }
        }

        if (origin.isInHanPalaceDiagonal() && destination.isInHanPalaceDiagonal()) {
            if (origin.isCenterOfHanPalace() || destination.isCenterOfHanPalace()) {
                return List.of();
            }
            if (origin.getRow() + destination.getRow() == 8 &&
                    origin.getColumn() + destination.getColumn() == 16) {
                return List.of(Position.of(4, 8));
            }
        }

        return null;
    }

    private List<Position> getDirectionalRoute(Position origin, int delta,
                                               Function<Position, Position> positiveMove,
                                               Function<Position, Position> negativeMove) {
        List<Position> intermediatePoints = new ArrayList<>();
        Function<Position, Position> moveFunction = getMoveFunction(delta, positiveMove, negativeMove);

        while (Math.abs(delta) > 1) {
            origin = moveFunction.apply(origin);
            intermediatePoints.add(origin);
            delta -= Integer.signum(delta);
        }

        return intermediatePoints;
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

        if (destinationPiece.getType() != PieceType.EMPTY && destinationPiece.getType().equals(PieceType.CANNON)) {
            throw new UnsupportedOperationException("[ERROR] 포는 포를 공격할 수 없습니다.");
        }

        List<Piece> pieces = IntermediatePointsWithPiece.values()
                .stream()
                .filter(piece -> piece.getType() != PieceType.EMPTY)
                .toList();

        if (pieces.size() != 1) {
            throw new UnsupportedOperationException("[ERROR] 포는 경로에 단 한개의 기물만 존재해야 합니다.");
        }

        if (pieces.getFirst().getType().equals(PieceType.CANNON)) {
            throw new UnsupportedOperationException("[ERROR] 포끼리 뛰어 넘을 수 없습니다.");
        }
    }

    @Override
    public PieceType getType() {
        return PieceType.CANNON;
    }
}
