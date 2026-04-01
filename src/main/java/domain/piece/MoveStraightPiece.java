package domain.piece;

import domain.Direction;
import domain.Palace;
import domain.Position;
import java.util.List;

public class MoveStraightPiece extends Piece {
    private static final String ONLY_MOVE_STRAIGHT = "[ERROR] 대각선으로 이동이 불가한 위치입니다.";
    private static final String FIXED_DIRECTION = "[ERROR] 해당 기물은 하나의 방향으로만 이동 가능합니다.";

    public MoveStraightPiece(PieceInfo pieceInfo) {
        super(pieceInfo);
    }

    @Override
    void validateDirections(List<Direction> directions, Position from, Position to) {
        Direction oneSide = directions.getFirst();
        boolean allSameDirection = directions.stream()
                .allMatch(direction -> direction.equals(oneSide));
        if (!allSameDirection) {
            throw new IllegalArgumentException(FIXED_DIRECTION);
        }
        if (oneSide.isDiagonal()) {
            validateDiagonalMove(from, to);
        }
    }

    private void validateDiagonalMove(Position from, Position to) {
        if (!Palace.canDiagonal(from)) {
            throw new IllegalArgumentException(ONLY_MOVE_STRAIGHT);
        }
        if (!Palace.isSamePalace(from, to)) {
            throw new IllegalArgumentException(ONLY_MOVE_STRAIGHT);
        }
    }
}
