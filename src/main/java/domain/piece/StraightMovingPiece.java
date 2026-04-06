package domain.piece;

import domain.board.Direction;
import domain.board.Position;
import dto.Distance;
import java.util.List;

public abstract class StraightMovingPiece extends Piece {
    private static final String ONLY_MOVE_STRAIGHT = "[ERROR] 직선으로만 이동 가능합니다.";
    private static final String FIXED_DIRECTION = "[ERROR] 하나의 방향으로만 이동 가능합니다.";

    public StraightMovingPiece(PieceInfo pieceInfo) {
        super(pieceInfo);
    }

    @Override
    public List<Direction> findMovingDirections(Position from, Position to) {
        Distance distance = from.calculateDistance(to);
        List<Direction> directions = Direction.findDirections(distance.x(), distance.y());

        if (from.isInPalace(pieceInfo.country())) {
            validateDirectionsInPalace(directions);
            return directions;
        }
        validateDirections(directions);
        return directions;
    }

    @Override
    protected void validateDirections(List<Direction> directions) {
        validateAllSameDirection(directions);
        if (directions.getFirst().isDiagonal()) {
            throw new IllegalArgumentException(ONLY_MOVE_STRAIGHT);
        }
    }

    protected void validateDirectionsInPalace(List<Direction> directions) {
        validateAllSameDirection(directions);
    }

    private void validateAllSameDirection(List<Direction> directions) {
        Direction oneSide = directions.getFirst();
        boolean allSameDirection = directions.stream()
                .allMatch(direction -> direction.equals(oneSide));
        if (!allSameDirection) {
            throw new IllegalArgumentException(FIXED_DIRECTION);
        }
    }
}
