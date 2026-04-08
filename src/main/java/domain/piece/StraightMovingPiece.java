package domain.piece;

import domain.board.Country;
import domain.board.Direction;
import domain.board.Position;
import java.util.List;

public abstract class StraightMovingPiece extends Piece {
    private static final String ONLY_MOVE_STRAIGHT = "[ERROR] 직선으로만 이동 가능합니다.";
    private static final String FIXED_DIRECTION = "[ERROR] 하나의 방향으로만 이동 가능합니다.";

    public StraightMovingPiece(PieceInfo pieceInfo) {
        super(pieceInfo);
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

    @Override
    protected boolean isInPalaceMove(Position from, Position to, Country country) {
        return country.isInPalace(from);
    }
}
